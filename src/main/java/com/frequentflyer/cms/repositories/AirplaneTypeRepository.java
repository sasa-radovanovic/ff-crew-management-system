package com.frequentflyer.cms.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.frequentflyer.cms.models.AirplaneType;


public interface AirplaneTypeRepository extends MongoRepository<AirplaneType, String> {

    public List<AirplaneType> findByManufacturer(String manufacturer);
    public AirplaneType findByType(String type);
    public AirplaneType findByTypeAndManufacturer(String type, String manufacturer);
    public AirplaneType findById(String id);
}
