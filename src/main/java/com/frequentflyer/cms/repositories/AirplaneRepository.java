package com.frequentflyer.cms.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.frequentflyer.cms.models.Airplane;

/**
 * 
 * Repository for handing airplane database operations
 * 
 * @author Sasa Radovanovic
 *
 */
public interface AirplaneRepository extends MongoRepository<Airplane, String> {

	Airplane findByRegistrationLike(String registration);
	
	Airplane findById(String id);
}
