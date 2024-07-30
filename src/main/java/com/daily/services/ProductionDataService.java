package com.daily.services;
import com.daily.repositories.ProductionDataRepository;
import com.daily.models.ProductionData;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionDataService {

    @Autowired
    private ProductionDataRepository productDataRepository;

    public List<?> getAllProductData() {
        return productDataRepository.findAll();
    }
}
