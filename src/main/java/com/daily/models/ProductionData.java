package com.daily.models;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.Date;


/**
 *
 * @author Rayen Boulehmi
 */

@Document(collection = "productdata")
@NoArgsConstructor
@Getter
@Setter
public class ProductionData {

                @Id
                private String id;

                private Date Date;

                @Field("NZONE")
                private String nzone;

                @Field("NameZONE")
                private String nameZone;

                @Field("NAtelier")
                private String atelierCode;

                @Field("NameAtelier_x")
                private String atelierName;

                @Field("ObjAbs")
                private String objAbs;

                @Field("TxAbs")
                private String txAbs;

                @Field("ObjPolyvalence")
                private String objPolyvalence;

                @Field("TxPolyvalence")
                private String txPolyvalence;

                @Field("ObjSafety")
                private String objSafety;

                @Field("NdaccidentAvecArrêt")
                private String ndAccidentAvecArret;

                @Field("RefProduit")
                private String refProduit;

                @Field("DésignationProduit")
                private String designationProduit;

                @Field("QtéProduite_x")
                private String qteProduiteX;

                @Field("QtéLivrée_x")
                private String qteLivreeX;

                @Field("NbrDéfautInt")
                private String nbrDefautInt;

                @Field("QtérebutInt")
                private String qteRebutInt;

                @Field("QtéRéclamée")
                private String qteReclamee;

                @Field("ObjNQInt")
                private String objNQInt;

                @Field("ObjDPU")
                private String objDPU;

                @Field("ObjNQExt")
                private String objNQExt;

                @Field("ObjPPMInt")
                private String objPPMInt;

                @Field("ObjPPMClient")
                private String objPPMClient;

                @Field("TxNQInt")
                private String txNQInt;

                @Field("DPU")
                private String dpu;

                @Field("TxNQExt")
                private String txNQExt;

                @Field("PPMRebutInt")
                private String ppmRebutInt;

                @Field("PPMClient")
                private String ppmClient;

                @Field("QtéProduite_y")
                private String qteProduiteY;

                @Field("QtéLancéePDP")
                private String qteLanceePDP;

                @Field("QtéEngClient")
                private String qteEngClient;

                @Field("QtéLivrée_y")
                private String qteLivreeY;

                @Field("ObjOTP")
                private String objOTP;

                @Field("ObjOTD")
                private String objOTD;

                @Field("OTD")
                private String otd;

                @Field("OTP")
                private String otp;

                @Field("QtéProduite")
                private String qteProduite;

                @Field("QtéLivrée")
                private String qteLivree;

                @Field("TpsdouvNette")
                private String tpsDouvNette;

                @Field("TpsSTDUnitaire")
                private String tpsSTDUnitaire;

                @Field("TpsPointeeNetProduit")
                private String tpsPointeeNetProduit;

                @Field("HrBadgées")
                private String hrBadgees;

                @Field("ETPthéo")
                private String etpTheorique;

                @Field("ETPRéel")
                private String etpReel;

                @Field("OBJEFFNET")
                private String objEffNet;

                @Field("OBJEFFBRUT")
                private String objEffBrut;

                @Field("EfficienceNette")
                private String efficienceNette;

                @Field("EfficienceBrute")
                private String efficienceBrute;

                @Field("NameAtelier_y")
                private String nameAtelierY;

                @Field("NUnité")
                private String nUnite;

                @Field("Unité_x")
                private String uniteX;

                @Field("NameUnit")
                private String nameUnit;

                @Field("Site")
                private String site;

                @Field("NameSite")
                private String nameSite;

                @Field("IsAbs")
                private String isAbs;

                @Field("IsPolyvalence")
                private String isPolyvalence;

                @Field("IsEffNet")
                private String isEffNet;

                @Field("IsEffBrut")
                private String isEffBrut;

                @Field("Categorie")
                private String categorie;

                @Field("Name Catégorie")
                private String nameCategorie;

                @Field("Ref")
                private String ref;

                @Field("Zone")
                private String zone;

                @Field("Atelier")
                private String atelier;

                @Field("Unité_y")
                private String uniteY;

                @Field("Produits")
                private String produits;

                @Field("Etat")
                private String etat;

        }
