package com.frequentflyer.cms.rest_controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.frequentflyer.cms.PrincipalChecker;
import com.frequentflyer.cms.models.Crew;
import com.frequentflyer.cms.models.Route;
import com.frequentflyer.cms.services.RouteService;

import webrequests.CreateRouteRequest;

/**
 * 
 * Rest endpoint to handle route operations
 * 
 * @author Sasa Radovanovic
 *
 */
@RestController
public class RoutesRestCtrl {
	
Logger logger = LoggerFactory.getLogger(RoutesRestCtrl.class);
	
	
	@Autowired
	RouteService routeService;
	
	@RequestMapping(value="/rest/api/routes/active/page/{page_num}",
			method = RequestMethod.GET)
	public ResponseEntity<Page<Route>> retrieveFlightsPage (@PathVariable("page_num") int page_num) {

		logger.info(" retrieveFlightsPage | called " + page_num);

		if (!PrincipalChecker.isAdmin(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return new ResponseEntity<Page<Route>>(HttpStatus.UNAUTHORIZED);
		}
		
		Page<Route> routes = null;
		
		try {
			routes = routeService.getPageRoutes(page_num);
		} catch (Exception e) {
			return new ResponseEntity<Page<Route>>(routes, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Page<Route>>(routes, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/rest/api/routes/active/create",
			method = RequestMethod.POST)
	public ResponseEntity<Route> createRoute (@RequestBody CreateRouteRequest request) {

		logger.info(" createRoute | called " );

		if (!PrincipalChecker.isAdmin(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		Route route = null;
		
		try {

		} catch (Exception e) {
			return new ResponseEntity<Route>(route, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Route>(route, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/rest/api/routes/active/retrieve/{route_id}",
			method = RequestMethod.GET)
	public ResponseEntity<Route> retrieveRoute (@PathVariable("route_id") String route_id) {

		logger.info(" retrieveRoute | called " );

		if (!PrincipalChecker.isAdmin(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		Route route = null;
		
		try {
			route = routeService.findRouteById(route_id);
		} catch (Exception e) {
			return new ResponseEntity<Route>(route, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Route>(route, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/rest/api/routes/active/delete/{route_id}",
			method = RequestMethod.GET)
	public ResponseEntity<Route> removeRoute (@PathVariable("route_id") String route_id) {

		logger.info(" removeRoute | called " );

		if (!PrincipalChecker.isAdmin(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		Route route = null;
		
		try {
			route = routeService.removeRoute(route_id);
		} catch (Exception e) {
			return new ResponseEntity<Route>(route, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Route>(route, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/rest/api/routes/active/assign/{crew_type}/{route_id}/{crew_id}",
			method = RequestMethod.GET)
	public ResponseEntity<Crew> assignCrewToRoute (@PathVariable("crew_type") String crew_type,
			@PathVariable("route_id") String route_id, @PathVariable("crew_id") String crew_id) {

		logger.info(" assignCrewToRoute | called " );

		if (!PrincipalChecker.isAdmin(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		Crew crew = null;
		
		try {
			crew = routeService.assignCrewToRoute(crew_type, crew_id, route_id);
		} catch (Exception e) {
			return new ResponseEntity<Crew>(crew, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Crew>(crew, HttpStatus.OK);
	}

}
