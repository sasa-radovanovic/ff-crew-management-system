package com.frequentflyer.cms.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frequentflyer.cms.models.Airplane;
import com.frequentflyer.cms.models.AirplaneType;
import com.frequentflyer.cms.repositories.AirplaneRepository;
import com.frequentflyer.cms.repositories.AirplaneTypeRepository;

@Service
public class AirplaneService {
	
	Logger logger = LoggerFactory.getLogger(AirplaneService.class);

	
	@Autowired
	AirplaneTypeRepository airplaneTypeRepository;
	
	@Autowired
	AirplaneRepository airplaneRepository;
	
	public AirplaneType getAirplaneTypeByTypeAndMan (String type, String manufacturer) {
		logger.info("getAirplaneTypeByTypeAndMan | type " + type + " manufacturer " + manufacturer);
		AirplaneType airplaneType = null;
		airplaneType = airplaneTypeRepository.findByTypeAndManufacturer(type, manufacturer);
		return airplaneType;
	}
	
	public AirplaneType createNewAirplaneType (AirplaneType airplaneType) {
		logger.info("createNewAirplaneType | create ");
		AirplaneType airplaneTypeSaved = null;
		airplaneTypeSaved = airplaneTypeRepository.save(airplaneType);
		return airplaneTypeSaved;
	}
	
	public AirplaneType updateAirplaneType (AirplaneType airplaneType) {
		logger.info("updateAirplaneType | update ");
		AirplaneType airplaneTypeUpdated = null;
		airplaneTypeUpdated = airplaneTypeRepository.save(airplaneType);
		return airplaneTypeUpdated;
	}
	
	public Airplane updateAirplane (String id, String registration, int seatCapacity) {
		logger.info("updateAirplane | update ");
		Airplane airplane = airplaneRepository.findById(id);
		airplane.setRegistration(registration);
		airplane.setSeatCapacity(seatCapacity);
		airplaneRepository.save(airplane);
		return airplane;
	}
	
	public ArrayList<AirplaneType> retrieveAllAirplaneTypes () {
		ArrayList<AirplaneType> retList = (ArrayList<AirplaneType>) airplaneTypeRepository.findAll();
		return retList;
	}
	
	public Airplane createNewAirplane (Airplane airplane) {
		logger.info("createNewAirplane | create ");
		Airplane airplaneToSave = airplaneRepository.save(airplane);
		return airplaneToSave;
	}
	
	public Airplane retrieveAirplane (String id) {
		logger.info("retrieveAirplane | retrieve ");
		Airplane airplane = airplaneRepository.findById(id);
		return airplane;
	}
	
	public AirplaneType retrieveAirplaneType (String id) {
		logger.info("retrieveAirplaneType | retrieve ");
		AirplaneType airplaneType = airplaneTypeRepository.findById(id);
		return airplaneType;
	}
	
	
	public List<Airplane> retrieveAirplanes () {
		logger.info("retrieveAirplanes | retrieve ");
		List<Airplane> airplanes = new ArrayList<Airplane>();
		airplanes =	airplaneRepository.findAll();
		return airplanes;
	}
	
	public Airplane updateAirplane (Airplane airplane) {
		logger.info("updateAirplane | update ");
		Airplane airplaneUpdated = airplaneRepository.save(airplane);
		return airplaneUpdated;
	}
	
	public void deleteAirplane (Airplane airplane) {
		airplaneRepository.delete(airplane);
	}
	
	public void deleteAirplaneType (AirplaneType airplaneType) {
		airplaneTypeRepository.delete(airplaneType);
	}
	
	
	public void removeAllFromFleet (AirplaneType airplaneType) {
		ArrayList<Airplane> airplanes = (ArrayList<Airplane>) this.retrieveAirplanes();
		for (Airplane airplane : airplanes) {
			if (airplane.getAirplaneType().getId().equalsIgnoreCase(airplaneType.getId())) {
				logger.info(" delete airplane | " + airplane.getRegistration());
				airplaneRepository.delete(airplane);
			}
		}
	}
	
}
