package com.daily.repositories;
import com.daily.models.ProductionData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductionDataRepository extends MongoRepository<ProductionData, String> {

}