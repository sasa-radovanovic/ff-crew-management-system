package com.frequentflyer.cms.rest_controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.frequentflyer.cms.PrincipalChecker;
import com.frequentflyer.cms.models.AirplaneType;
import com.frequentflyer.cms.models.ScheduledRoute;
import com.frequentflyer.cms.models.helpers.SortedScheduledRoutes;
import com.frequentflyer.cms.services.AirplaneService;
import com.frequentflyer.cms.services.RouteService;

import webrequests.CreateScheduledRouteRequest;

/**
 * 
 * Rest endpoint to handle scheduled route operations
 * 
 * @author Sasa Radovanovic
 *
 */
@RestController
public class ScheduledRoutesRestCtrl {
	
	Logger logger = LoggerFactory.getLogger(ScheduledRoutesRestCtrl.class);
	
	
	@Autowired
	RouteService routeService;
	
	@Autowired
	AirplaneService airplaneService;
	
	@RequestMapping(value="/rest/api/scheduled_routes/create",
			method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> createScheduledRoute(@RequestBody CreateScheduledRouteRequest request) {
		
		logger.info(" createScheduledRoute | called");
		
		if (!PrincipalChecker.isAdmin(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		ScheduledRoute newScheduledRoute = null;

		try {
			logger.info(" createScheduledRoute | TYPE " + request.getAirplaneTypeId() + " MANUFACTURER " + request.getAirplaneTypeManufacturer());
			AirplaneType airplaneType = airplaneService.getAirplaneTypeByTypeAndMan(request.getAirplaneTypeId(), 
					request.getAirplaneTypeManufacturer());
			newScheduledRoute = new ScheduledRoute(request.getName(), 
					airplaneType, 
					request.getDaysOfTheWeek(), 
					request.getDeparture(), 
					request.getArrival(), 
					request.getLocalDepartureTime(), 
					request.getLocalArrivalTime(), 
					request.getLength());
			routeService.saveNewScheduledRoute(newScheduledRoute);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/rest/api/scheduled_routes/retrieve/{routeId}",
			method = RequestMethod.GET)
	public ResponseEntity<ScheduledRoute> retrieveScheduledRoute (@PathVariable("routeId") String routeId) {
		
		ScheduledRoute scheduledRoute = null;
		
		logger.info(" retrieveScheduledRoute | called " + routeId);

		try {
			scheduledRoute = routeService.retrieveScheduledRouteById(routeId);
		} catch (Exception e) {
			return new ResponseEntity<ScheduledRoute>(scheduledRoute, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ScheduledRoute>(scheduledRoute, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/rest/api/scheduled_routes/retrieve_sorted",
			method = RequestMethod.GET)
	public ResponseEntity<SortedScheduledRoutes> retrieveSortedScheduledRoutes () {
				
		if (!PrincipalChecker.isAdmin(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		SortedScheduledRoutes sortedRoutes = new SortedScheduledRoutes();

		try {
			sortedRoutes = routeService.retrieveSortedScheduledRoutes();
		} catch (Exception e) {
			return new ResponseEntity<SortedScheduledRoutes>(sortedRoutes, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<SortedScheduledRoutes>(sortedRoutes, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/rest/api/scheduled_routes/remove/{routeId}",
			method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteScheduledRoute (@PathVariable("routeId") String routeId) {
	
		
		if (!PrincipalChecker.isAdmin(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {
			routeService.removeScheduledRoute(routeId);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}


}
