package com.frequentflyer.cms.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.frequentflyer.cms.models.Route;


/**
 * 
 * Repository for handing routes
 * 
 * @author Sasa Radovanovic
 *
 */
public interface RouteRepository extends MongoRepository<Route, String> {

	public List<Route> findByDeparture(String departure);
	
	public Route findById(String id);
	
	public Page<Route> findAll (Pageable pageable);
}
