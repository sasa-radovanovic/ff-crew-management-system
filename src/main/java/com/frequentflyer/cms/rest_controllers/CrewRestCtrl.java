package com.frequentflyer.cms.rest_controllers;

import java.util.ArrayList;

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

import com.frequentflyer.cms.Constants;
import com.frequentflyer.cms.PrincipalChecker;
import com.frequentflyer.cms.models.Crew;
import com.frequentflyer.cms.models.helpers.CrewRoute;
import com.frequentflyer.cms.services.CrewService;

import webrequests.AdvancedSearchCrew;
import webrequests.CreateCrewRequest;

/**
 * 
 * This class implements Crew Rest Controller which exposes Crew API.
 * 
 * author Sasa Radovanovic
 * 
 * */
@RestController
public class CrewRestCtrl {



	Logger logger = LoggerFactory.getLogger(GeneralApplicationRestCtrl.class);

	@Autowired
	CrewService crewService;

	@RequestMapping(value="/rest/api/crew/retrieve/{username}",
			method = RequestMethod.GET)
	public ResponseEntity<Crew> retrieveCrewProfile (@PathVariable("username") String username) {

		logger.info(" retrieveCrewProfile | called " + username);

		if (!PrincipalChecker.isAdminOrThisUser(username, SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return new ResponseEntity<Crew>(HttpStatus.UNAUTHORIZED);
		}
		
		Crew crew = null;
		
		try {
			crew = crewService.retrieveUserByUsername(username);
		} catch (Exception e) {
			return new ResponseEntity<Crew>(crew, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Crew>(crew, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/rest/api/cabin_crew/page/{page_num}",
			method = RequestMethod.GET)
	public ResponseEntity<Page<Crew>> retrieveCabinCrewPage (@PathVariable("page_num") int page_num) {

		logger.info(" retrieveCabinCrewPage | called " + page_num);

		if (!PrincipalChecker.isAdmin(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return new ResponseEntity<Page<Crew>>(HttpStatus.UNAUTHORIZED);
		}
		
		Page<Crew> crews = null;
		
		try {
			crews = crewService.getPageCabinCrew(page_num);
		} catch (Exception e) {
			return new ResponseEntity<Page<Crew>>(crews, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Page<Crew>>(crews, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/rest/api/flight_crew/page/{page_num}",
			method = RequestMethod.GET)
	public ResponseEntity<Page<Crew>> retrieveFlightCrewPage (@PathVariable("page_num") int page_num) {

		logger.info(" retrieveFlightCrewPage | called " + page_num);

		if (!PrincipalChecker.isAdmin(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return new ResponseEntity<Page<Crew>>(HttpStatus.UNAUTHORIZED);
		}
		
		Page<Crew> crews = null;
		
		try {
			crews = crewService.getPageFlightCrew(page_num);
		} catch (Exception e) {
			return new ResponseEntity<Page<Crew>>(crews, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Page<Crew>>(crews, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/rest/api/crew/create",
			method = RequestMethod.POST)
	public ResponseEntity<Crew> createCrew(@RequestBody CreateCrewRequest request) {
		
		if (!PrincipalChecker.isAdmin(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if (!request.getCrewType().equalsIgnoreCase(Constants.CREW_CABIN_CREW) && !request.getCrewType().equalsIgnoreCase(Constants.CREW_FLIGHT_CREW)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Crew crew = new Crew(request.getFirstName(), request.getLastName(), request.getUsername(), request.getCompanyId(),
				request.getMail(), false, new ArrayList<CrewRoute>(), request.getCrewType(), 
				request.getLanguagesSpoken(), request.getCountryOfOrigin(), request.getCompanyId());
		
		logger.info(" createCrew | called ");
		logger.info(" createCrew | " + crew.toString());

		try {
			crew = crewService.createCrew(crew);
		} catch (Exception e) {
			return new ResponseEntity<Crew>(crew, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Crew>(crew, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/rest/api/crew/delete/{crew_type}/{crew_id}",
			method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteCrew(@PathVariable("crew_type") String crewType, 
			@PathVariable("crew_id") String crewId) {
	
		logger.info(" | delete crew called");
		
		if (!PrincipalChecker.isAdmin(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	
		if (!crewType.equalsIgnoreCase(Constants.CREW_CABIN_CREW) && !crewType.equalsIgnoreCase(Constants.CREW_FLIGHT_CREW)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		logger.info(" | delete crew type  " + crewId);

		try {
			crewService.deleteCrew(crewType, crewId);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@RequestMapping(value="/rest/api/crew/size/{type}",
			method = RequestMethod.GET)
	public ResponseEntity<Integer> getCrewSize (@PathVariable("type") String type) {

		logger.info(" getCrewSize | called " + type);

		if (!PrincipalChecker.isAdmin(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return new ResponseEntity<Integer>(HttpStatus.UNAUTHORIZED);
		}
		
		if (!type.equalsIgnoreCase(Constants.CREW_CABIN_CREW) 
				&& !type.equalsIgnoreCase(Constants.CREW_FLIGHT_CREW)) {
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		}
		
		int size = 0;
		
		try {
			size = crewService.getCrewSize(type);
		} catch (Exception e) {
			return new ResponseEntity<Integer>(size, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Integer>(size, HttpStatus.OK);
	}
	
	
	
	
	@RequestMapping(value="/rest/api/crew/advanced_search/page/{page_num}/{crew_type}",
			method = RequestMethod.POST)
	public ResponseEntity<Page<Crew>> advancedSearchCrew (@PathVariable("page_num") int page_num, 
			@PathVariable("crew_type") String crew_type, @RequestBody AdvancedSearchCrew request) {

		logger.info(" advancedSearchCrew | called " + page_num + " crew type " + crew_type);

		if (!PrincipalChecker.isAdmin(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return new ResponseEntity<Page<Crew>>(HttpStatus.UNAUTHORIZED);
		}
		
		if (!crew_type.equalsIgnoreCase(Constants.CREW_CABIN_CREW) && !crew_type.equalsIgnoreCase(Constants.CREW_FLIGHT_CREW)) {
			return new ResponseEntity<Page<Crew>>(HttpStatus.BAD_REQUEST);
		}
		
		Page<Crew> crews = null;
		
		try {
			crews = crewService.doAdvancedSearch(crew_type, 
					request.getCriteriaParam(), request.getCriteriaValue(), (page_num - 1)); 
		} catch (Exception e) {
			return new ResponseEntity<Page<Crew>>(crews, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Page<Crew>>(crews, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/rest/api/crew/unassign_route/{crew_type}/{crew_id}/{route_id}",
			method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> unassignActiveRoute (@PathVariable("crew_type") String crew_type, 
			@PathVariable("crew_id") String crew_id, @PathVariable("route_id") String route_id) {

		logger.info(" unassignActiveRoute | called " + crew_id + " " + route_id);

		if (!PrincipalChecker.isAdmin(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if (!crew_type.equalsIgnoreCase(Constants.CREW_CABIN_CREW) && !crew_type.equalsIgnoreCase(Constants.CREW_FLIGHT_CREW)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		
		try {
			boolean result = crewService.unassignActiveRoute(crew_type, crew_id, route_id); 
			if (!result) {
				return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/rest/api/crew/update",
			method = RequestMethod.POST)
	public ResponseEntity<Crew> updateCrew (@RequestBody CreateCrewRequest request) {

		logger.info(" updateCrew | called " + request.getUsername());

		if (!PrincipalChecker.isAdmin(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if (!request.getCrewType().equalsIgnoreCase(Constants.CREW_CABIN_CREW) && !request.getCrewType().equalsIgnoreCase(Constants.CREW_FLIGHT_CREW)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Crew crew = new Crew(request.getFirstName(), request.getLastName(), request.getUsername(), request.getCompanyId(),
				request.getMail(), false, new ArrayList<CrewRoute>(), request.getCrewType(), 
				request.getLanguagesSpoken(), request.getCountryOfOrigin(), request.getCompanyId());
		
		Crew updated = null;
		
		try {
			updated = crewService.updateCrew(crew);
		} catch (Exception e) {
			return new ResponseEntity<Crew>(updated, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Crew>(updated, HttpStatus.OK);
	}

}
