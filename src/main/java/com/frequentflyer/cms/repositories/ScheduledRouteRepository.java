package com.frequentflyer.cms.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.frequentflyer.cms.models.ScheduledRoute;

/**
 * 
 * Repository for handing scheduled routes
 * 
 * @author Sasa Radovanovic
 *
 */
public interface ScheduledRouteRepository  extends MongoRepository<ScheduledRoute, String> {

	List<ScheduledRoute> findByNameLike(String name);
	
	@SuppressWarnings("rawtypes")
	List<ScheduledRoute> findByDaysOfTheWeek(ArrayList days);
	
	ScheduledRoute findById(String id);
	
	List<ScheduledRoute> findAll();
	
}
