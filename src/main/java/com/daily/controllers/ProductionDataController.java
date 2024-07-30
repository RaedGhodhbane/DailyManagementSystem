package com.daily.controllers;

import com.daily.models.ProductionData;
import com.daily.repositories.ProductionDataRepository;
import com.daily.services.ProductionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.bson.Document;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/kpi")
public class ProductionDataController {

    @Autowired
    private ProductionDataService productionDataService;

    private final ProductionDataRepository repository;

    public ProductionDataController(ProductionDataRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/all")
    public List<?> getAllProductData() {
        return productionDataService.getAllProductData();

    }

    @GetMapping("/all-data")
    public List<Document> getAllData() {
        List<Document> allData = mongoTemplate.findAll(Document.class, "productdata");
        allData.forEach(data -> System.out.println(data.toJson()));
        return allData; // Return the data in the response
    }


    @GetMapping("/volume-de-production")
    public Map<LocalDate, Double> calculateVolumeDeProduction() {
        List<Document> allData = mongoTemplate.findAll(Document.class, "productdata");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return allData.stream()
                .collect(Collectors.groupingBy(
                        data -> LocalDate.parse(data.getString("Date"), formatter),
                        Collectors.summingDouble(data -> data.getInteger("QtéProduite"))
                ));
    }

    @GetMapping("/notification")
    public Map<String, Map<String, Long>> notification() {
        List<Document> allData = mongoTemplate.findAll(Document.class, "productdata");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Map<String, Map<String, Long>> result = allData.stream()
                .collect(Collectors.groupingBy(
                        data -> data.getString("Date"),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                dataList -> {
                                    Map<String, Long> aggregatedValues = new HashMap<>();
                                    aggregatedValues.put("IsEffBrut", dataList.stream().mapToLong(d -> d.getInteger("IsEffBrut", 0)).sum());
                                    aggregatedValues.put("IsEffNet", dataList.stream().mapToLong(d -> d.getInteger("IsEffNet", 0)).sum());
                                    aggregatedValues.put("IsPolyvalence", dataList.stream().mapToLong(d -> d.getInteger("IsPolyvalence", 0)).sum());
                                    aggregatedValues.put("IsAbs", dataList.stream().mapToLong(d -> d.getInteger("IsAbs", 0)).sum());
                                    return aggregatedValues;
                                }
                        )
                ));

        // Adding total counts
        Map<String, Long> totalCounts = new HashMap<>();
        totalCounts.put("TotalIsEffBrut", result.values().stream().mapToLong(map -> map.getOrDefault("IsEffBrut", 0L)).sum());
        totalCounts.put("TotalIsEffNet", result.values().stream().mapToLong(map -> map.getOrDefault("IsEffNet", 0L)).sum());
        totalCounts.put("TotalIsPolyvalence", result.values().stream().mapToLong(map -> map.getOrDefault("IsPolyvalence", 0L)).sum());
        totalCounts.put("TotalIsAbs", result.values().stream().mapToLong(map -> map.getOrDefault("IsAbs", 0L)).sum());

        // Adding the totals to the result with a special key
        result.put("TOTALS", totalCounts);

        return result;
    }

    @GetMapping("/livraisons")
    public Map<LocalDate, Double> calculateLivraisons() {
        List<Document> allData = mongoTemplate.findAll(Document.class, "productdata");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return allData.stream()
                .collect(Collectors.groupingBy(
                        data -> LocalDate.parse(data.getString("Date"), formatter),
                        Collectors.summingDouble(data -> data.getInteger("QtéLivrée"))
                ));
    }

    @GetMapping("/chiffre-daffaires")
    public Map<String, Map<String, Double>> getChiffreDAffaires() {
        List<Document> allData = mongoTemplate.findAll(Document.class, "productdata");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return allData.stream()
                .collect(Collectors.groupingBy(
                        data -> LocalDate.parse(data.getString("Date"), formatter).toString(), // First grouping by date
                        Collectors.groupingBy(
                                data -> data.getString("RéfProduit"), // Then grouping by product reference
                                Collectors.summingDouble(data -> {
                                    int qtyLivree = data.getInteger("QtéLivrée");
                                    int tpsSTDUnitaire = data.getInteger("TpsSTD.Unitaire(s)");
                                    return (double) qtyLivree * tpsSTDUnitaire;
                                })
                        )
                ));
    }

    @GetMapping("/couts")
    public Map<String, Map<String, Double>> getCosts() {
        List<Document> allData = mongoTemplate.findAll(Document.class, "productdata");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return allData.stream()
                .collect(Collectors.groupingBy(
                        data -> LocalDate.parse(data.getString("Date"), formatter).toString(), // Group by date
                        Collectors.groupingBy(
                                data -> data.getString("RéfProduit"), // Then group by product reference
                                Collectors.summingDouble(data -> {
                                    int qtyProduite = data.getInteger("QtéProduite");
                                    int tpsSTDUnitaire = data.getInteger("TpsSTD.Unitaire(s)");
                                    return (double) qtyProduite * tpsSTDUnitaire;
                                })
                        )
                ));
    }

    @GetMapping("/quantite-retardee")
    public Map<String, Map<String, Integer>> getQuantiteRetardee() {
        List<Document> allData = mongoTemplate.findAll(Document.class, "productdata");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return allData.stream()
                .collect(Collectors.groupingBy(
                        data -> LocalDate.parse(data.getString("Date"), formatter).toString(), // Group by date
                        Collectors.groupingBy(
                                data -> data.getString("RéfProduit"), // Then group by product reference
                                Collectors.summingInt(data -> {
                                    int qteLanceePDP = data.getInteger("QtéLancéePDP");
                                    int qteLivree = data.getInteger("QtéLivrée");
                                    return qteLanceePDP > qteLivree ? qteLanceePDP - qteLivree : 0;
                                })
                        )
                ));
    }

    @GetMapping("/defauts")
    public Map<String, Integer> getDefauts() {
        // Récupérer toutes les données de la collection "productdata"
        List<ProductionData> allData = mongoTemplate.findAll(ProductionData.class, "productdata");

        // Définir le format de date attendu (adapter au format réel des dates dans MongoDB)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Adapter si nécessaire

        // Regrouper les données par date et sommer les défauts
        return allData.stream()
                .collect(Collectors.groupingBy(
                        data -> {
                            // Convertir la date stockée en MongoDB en LocalDate
                            LocalDate date = data.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                            return date.format(formatter); // Formater la date en "yyyy-MM-dd"
                        },
                        Collectors.summingInt(data -> {
                            // Récupérer le nombre de défauts; adapter au champ spécifique dans vos données
                            return parseInteger(data.getNbrDefautInt()); // Assurez-vous que ce champ existe et est correct
                        })
                ));
    }


    @GetMapping("/chutes-de-production")
    public Map<String, Map<String, Integer>> getChutesDeProduction() {
        List<ProductionData> allData = mongoTemplate.findAll(ProductionData.class, "productdata");

        // Utiliser le format approprié pour les dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

        return allData.stream()
                .collect(Collectors.groupingBy(
                        data -> {
                            // Convertir la date au format approprié
                            String dateString = data.getDate().toString(); // Supposons que cela retourne la date sous forme de String
                            return LocalDate.parse(dateString, formatter).toString(); // Group by date
                        },
                        Collectors.groupingBy(
                                ProductionData::getRefProduit, // Group by product reference
                                Collectors.summingInt(data -> {
                                    int qteReclamee = parseInteger(data.getQteReclamee());
                                    int nbrDefautInt = parseInteger(data.getNbrDefautInt());
                                    int qteRebutInt = parseInteger(data.getQteRebutInt());
                                    return qteReclamee + nbrDefautInt + qteRebutInt;
                                })
                        )
                ));
    }

    @GetMapping("/nombre-de-pannes")
    public Map<String, Integer> getNombreDePannes() {
        // Assurez-vous que la classe ProductionData est correctement mappée pour vos données
        List<ProductionData> allData = mongoTemplate.findAll(ProductionData.class, "productdata");

        // Utiliser le format de date correct en fonction des données dans MongoDB
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Changez si nécessaire

        // Collecter les données en les groupant par date et en sommant les pannes
        return allData.stream()
                .collect(Collectors.groupingBy(
                        data -> {
                            // Convertir la date au format LocalDate ou utiliser la date directement si elle est déjà en bon format
                            LocalDate date = data.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                            return date.format(formatter); // Formater la date comme "yyyy-MM-dd"
                        },
                        Collectors.summingInt(data -> {
                            // Récupérer le nombre de pannes, gérer les valeurs nulles
                            return parseInteger(data.getNdAccidentAvecArret());
                        })
                ));
    }

    @GetMapping("/maintenance-corrective")
    public Map<String, Integer> getMaintenanceCorrective() {
        // Récupérer toutes les données de la collection "productdata"
        List<ProductionData> allData = mongoTemplate.findAll(ProductionData.class, "productdata");

        // Définir le format de date attendu (adapter au format réel des dates dans MongoDB)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Regrouper les données par date et sommer les opérations de maintenance corrective
        return allData.stream()
                .collect(Collectors.groupingBy(
                        data -> {
                            // Convertir la date stockée en MongoDB en LocalDate
                            LocalDate date = data.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            return date.format(formatter); // Formater la date en "yyyy-MM-dd"
                        },
                        Collectors.summingInt(data -> {
                            // Récupérer le nombre d'accidents avec arrêt; adapter le champ si nécessaire
                            return parseInteger(data.getNdAccidentAvecArret()); // Assurez-vous que ce champ existe et est correct
                        })
                ));
    }

    @GetMapping("/absenteisme")
    public Map<String, Integer> getAbsenteisme() {
        // Récupérer toutes les données de la collection "productdata"
        List<ProductionData> allData = mongoTemplate.findAll(ProductionData.class, "productdata");

        // Convertir les valeurs de champ en entier pour calculer l'absentéisme
        return allData.stream()
                .collect(Collectors.groupingBy(
                        data -> {
                            // Grouper par date de production
                            LocalDate productionDate = toLocalDate(data.getDate());
                            return productionDate.toString();
                        },
                        Collectors.summingInt(data -> {
                            try {
                                // Convertir la chaîne en nombre pour le champ 'TxAbs'
                                NumberFormat format = NumberFormat.getInstance();
                                Number number = format.parse(data.getTxAbs());
                                return number.intValue();
                            } catch (ParseException e) {
                                // En cas d'erreur de conversion, retourner 0 ou gérer l'erreur
                                return 0;
                            }
                        })
                ));
    }

    // Méthode utilitaire pour convertir Date en LocalDate
    private LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }


    private int parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
