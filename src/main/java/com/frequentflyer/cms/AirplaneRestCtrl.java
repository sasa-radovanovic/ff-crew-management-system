package com.frequentflyer.cms;

import java.util.ArrayList;

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

import com.frequentflyer.cms.models.Airplane;
import com.frequentflyer.cms.models.AirplaneType;
import com.frequentflyer.cms.services.AirplaneService;

import webrequests.AirplaneTypeRequest;
import webrequests.CreateAirplaneRequest;
import webrequests.UpdateAirplaneRequest;

@RestController
public class AirplaneRestCtrl {

	Logger logger = LoggerFactory.getLogger(AirplaneRestCtrl.class);

	
	@Autowired
	AirplaneService airplaneService;
	
	
	@RequestMapping(value="/rest/api/airplane_types/retrieve",
			method = RequestMethod.POST)
	public ResponseEntity<AirplaneType> retrieveAirplaneTypes(@RequestBody AirplaneTypeRequest request) {
		
		AirplaneType airplaneType = null;

		try {
			airplaneType = airplaneService.getAirplaneTypeByTypeAndMan("747-800", "Boeing");
		} catch (Exception e) {
			return new ResponseEntity<AirplaneType>(airplaneType, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<AirplaneType>(airplaneType, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/rest/api/airplane_types/retrieve_all",
			method = RequestMethod.GET)
	public ResponseEntity<ArrayList<AirplaneType>> retrieveAllAirplaneTypes() {
		
		if (!PrincipalChecker.isAdmin(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		ArrayList<AirplaneType> returnList = new ArrayList<AirplaneType>();
		
		try {
			returnList = airplaneService.retrieveAllAirplaneTypes();
		} catch (Exception e) {
			return new ResponseEntity<ArrayList<AirplaneType>>(returnList, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArrayList<AirplaneType>>(returnList, HttpStatus.OK);
	}
	
	@RequestMapping(value="/rest/api/airplane_types/create",
			method = RequestMethod.POST)
	public ResponseEntity<AirplaneType> createAirplaneType(@RequestBody AirplaneTypeRequest request) {
		
		if (!PrincipalChecker.isAdmin(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		AirplaneType airplaneType = new AirplaneType(request.getManufacturer(), request.getType());
		
		logger.info(" createAirplaneType | called " + request.getType() + " " + request.getManufacturer());

		try {
			airplaneType = airplaneService.createNewAirplaneType(airplaneType);
		} catch (Exception e) {
			return new ResponseEntity<AirplaneType>(airplaneType, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<AirplaneType>(airplaneType, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/rest/api/airplane/create",
			method = RequestMethod.POST)
	public ResponseEntity<Airplane> createAirplane(@RequestBody CreateAirplaneRequest request) {
		
		if (!PrincipalChecker.isAdmin(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		Airplane airplane = null;
		
		logger.info(" createAirplane | called ");

		try {
			AirplaneType airplaneType = airplaneService.getAirplaneTypeByTypeAndMan(request.getType(), request.getManufacturer());
			airplane = new Airplane(airplaneType, request.getRegistration(), request.getSeatCapacity(),
					request.getDeliveryDate(), request.getMsnNumber());
			airplane = airplaneService.createNewAirplane(airplane);
		} catch (Exception e) {
			return new ResponseEntity<Airplane>(airplane, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Airplane>(airplane, HttpStatus.CREATED);
	}
	
	
	
	@RequestMapping(value="/rest/api/airplane/update",
			method = RequestMethod.POST)
	public ResponseEntity<Airplane> updateAirplane(@RequestBody UpdateAirplaneRequest request) {
		
		if (!PrincipalChecker.isAdmin(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		Airplane airplane = null;
		
		logger.info(" updateAirplane | called ");

		try {
			airplane = airplaneService.updateAirplane(request.getId(), request.getRegistration(), request.getSeatCapacity());
		} catch (Exception e) {
			return new ResponseEntity<Airplane>(airplane, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Airplane>(airplane, HttpStatus.OK);
	}
	
	@RequestMapping(value="/rest/api/airplane/retrieve/{airplaneId}",
			method = RequestMethod.GET)
	public ResponseEntity<Airplane> retrieveAirplane (@PathVariable("airplaneId") String id) {
		
		Airplane airplane = null;
		
		logger.info(" retrieveAirplane | called " + id);

		try {
			airplane = airplaneService.retrieveAirplane(id);
		} catch (Exception e) {
			return new ResponseEntity<Airplane>(airplane, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Airplane>(airplane, HttpStatus.OK);
	}
	
	@RequestMapping(value="/rest/api/airplane/retrieve_all",
			method = RequestMethod.GET)
	public ResponseEntity<ArrayList<Airplane>> retrieveAirplane () {
		
		if (!PrincipalChecker.isAdmin(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		ArrayList<Airplane> retList = new ArrayList<Airplane>();
		
		logger.info(" retrieveAirplanes | called ");

		try {
			retList = (ArrayList<Airplane>) airplaneService.retrieveAirplanes();
		} catch (Exception e) {
			return new ResponseEntity<ArrayList<Airplane>>(retList, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArrayList<Airplane>>(retList, HttpStatus.OK);
	}
	
	@RequestMapping(value="/rest/api/airplane/update/{airplaneId}",
			method = RequestMethod.PUT)
	public ResponseEntity<Airplane> updateAirplane (@PathVariable("airplaneId") String id, @RequestBody CreateAirplaneRequest request) {
		
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}
	
	
	@RequestMapping(value="/rest/api/airplane/{airplaneId}",
			method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteAirplane (@PathVariable("airplaneId") String id) {
		
		if (!PrincipalChecker.isAdmin(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		logger.info(" deleteAirplane | called " + id);

		try {
			Airplane airplane = airplaneService.retrieveAirplane(id);
			airplaneService.deleteAirplane(airplane);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/rest/api/airplane_type/{airplaneTypeId}",
			method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteAirplaneType (@PathVariable("airplaneTypeId") String id) {
		
		if (!PrincipalChecker.isAdmin(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		logger.info(" deleteAirplane | called " + id);

		try {
			AirplaneType airplaneType = airplaneService.retrieveAirplaneType(id);
			airplaneService.removeAllFromFleet(airplaneType);
			airplaneService.deleteAirplaneType(airplaneType);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
