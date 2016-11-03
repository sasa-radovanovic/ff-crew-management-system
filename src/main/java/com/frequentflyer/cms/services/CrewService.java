package com.frequentflyer.cms.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.frequentflyer.cms.Constants;
import com.frequentflyer.cms.authentication.PasswordEncoder;
import com.frequentflyer.cms.models.Crew;
import com.frequentflyer.cms.models.Route;
import com.frequentflyer.cms.models.helpers.CrewRoute;
import com.frequentflyer.cms.repositories.CrewRepository;
import com.frequentflyer.cms.repositories.RouteRepository;

@Service
public class CrewService {
	
	Logger logger = LoggerFactory.getLogger(CrewService.class);
	
    @Autowired
    PasswordEncoder passwordEncoder;

	@Autowired
	CrewRepository crewRepository;
	
	@Autowired
	RouteRepository routeRepository;
	
	public Crew retrieveUserByUsername(String username) {
		return crewRepository.findByUsername(username);
	}
	
	
	public Crew createCrew(Crew newCrew) {
		newCrew.setPassword(passwordEncoder.bCryptPasswordEncoder().encode(newCrew.getPassword()));
		Crew savedCrew = crewRepository.save(newCrew);
		return savedCrew;
	}
	
	public Page<Crew> getPageCabinCrew(int page) {
		Page<Crew> crews = crewRepository.findAllByCrewType(Constants.CREW_CABIN_CREW, new PageRequest(page, 10));
		return crews;
	}
	
	public Page<Crew> getPageFlightCrew(int page) {
		Page<Crew> crews = crewRepository.findAllByCrewType(Constants.CREW_FLIGHT_CREW, new PageRequest(page, 10));
		return crews;
	}


	public int getCrewSize(String type) {
		int crewSize = -1;
		crewSize = (int) crewRepository.countByCrewType(type);
		return crewSize;
	}


	public void deleteCrew(String crewType, String crewId) throws Exception {
		logger.info(" | Remove crew " + crewType + " " + crewId);
		Crew crew = crewRepository.findById(crewId);
		if (crew == null) {
			logger.info(" | Remove crew not found");
			throw new Exception("NOT_FOUND");
		}
		logger.info(" | Remove crew Found crew");
		logger.info(" | Remove crew assigned routes " + crew.getAssignedRoutes().size());
		if (crew.getAssignedRoutes().size() > 0) {
			for (CrewRoute crewRoute : crew.getAssignedRoutes()) {
				Route route = routeRepository.findById(crewRoute.getRouteId());
				if (route != null) {
					HashMap<String, ArrayList<String>> crewMap = route.getAssignedCrew();
					ArrayList<String> crewIds = crewMap.get(crewType);
					for (String crewLocalId : crewIds) {
						if (crewLocalId.equalsIgnoreCase(crewId)) {
							crewIds.remove(crewLocalId);
						}
					}
					crewMap.replace(crewType, crewIds);
					route.setAssignedCrew(crewMap);
					routeRepository.save(route);
				}
			}
			
		}
		crewRepository.delete(crew);
	}
	
	public boolean unassignActiveRoute (String crewType, String crewId, String routeId) throws Exception {
		logger.info(" | Unassign route form Crew " + crewId + " " + routeId);
		Crew crew = crewRepository.findById(crewId);
		if (crew == null) {
			logger.info(" | unassignActiveRoute crew not found");
			throw new Exception("NOT_FOUND");
		}
		logger.info(" | Found crew " + crew.getFirstName() + " " + crew.getLastName());
		if (crew.getAssignedRoutes().size() > 0) {
			ArrayList<CrewRoute> routesToUpdate = crew.getAssignedRoutes();
			for (CrewRoute crewRoute : routesToUpdate) {
				if (crewRoute.getRouteId().equalsIgnoreCase(routeId)) {
					routesToUpdate.remove(crewRoute);
					crew.setAssignedRoutes(routesToUpdate);
					crewRepository.save(crew);
					Route route = routeRepository.findById(routeId);
					if (route == null) {
						return false;
					}
					logger.info(" | Found route " + route.getDeparture() + " " + route.getArrival());
					HashMap<String, ArrayList<String>> crews = route.getAssignedCrew();
					ArrayList<String> updatedCrew = crews.get(crewType);
					if (updatedCrew.size() > 0) {
						for (String crewInArray : updatedCrew) {
							if (crewInArray.equalsIgnoreCase(crewId)) {
								logger.info(" | Found userId in route ");
								updatedCrew.remove(crewInArray);
								route.setAssignedCrew(crews);
								routeRepository.save(route);
								logger.info(" | updated route");
								return true;
							}
						}
					}
				}	
			}
		}
		return false;
	}
	
	public boolean assignActiveRoute (String crewType, String crewId, String routeId) throws Exception {
		logger.info(" | assignActiveRoute route Crew " + crewId + " " + routeId);
		Crew crew = crewRepository.findById(crewId);
		if (crew == null) {
			logger.info(" | assignActiveRoute crew not found");
			throw new Exception("NOT_FOUND");
		}
		logger.info(" | Found crew " + crew.getFirstName() + " " + crew.getLastName());
		ArrayList<CrewRoute> routesToUpdate = crew.getAssignedRoutes();
		Route route = routeRepository.findById(routeId);	
		CrewRoute crewRoute = new CrewRoute(routeId, route.getLocalDepartureTime(), 
				route.getLength(), route.getDeparture(), route.getArrival(), route.getFlightNumber());
		routesToUpdate.add(crewRoute);
		crew.setAssignedRoutes(routesToUpdate);
		crewRepository.save(crew);
		ArrayList<String> crewIds = route.getAssignedCrew().get(crewType);
		crewIds.add(crewId);
		routeRepository.save(route);
		return true;
	}
	
	
	public Page<Crew> getPageCabinCrewByFirstName(String firstName, int page) {
		Page<Crew> crews = crewRepository.findAllByCrewTypeAndFirstNameLike(Constants.CREW_CABIN_CREW, firstName, new PageRequest(page, 10));
		return crews;
	}
	
	public Page<Crew> getPageCabinCrewByLastName(String lastName, int page) {
		Page<Crew> crews = crewRepository.findAllByCrewTypeAndLastNameLike(Constants.CREW_CABIN_CREW, lastName, new PageRequest(page, 10));
		return crews;
	}
	
	public Page<Crew> getPageCabinCrewByCompanyId(String companyId, int page) {
		Page<Crew> crews = crewRepository.findAllByCrewTypeAndCompanyIdLike(Constants.CREW_CABIN_CREW, companyId, new PageRequest(page, 10));
		return crews;
	}
	
	public Page<Crew> getPageFlightCrewByFirstName(String firstName, int page) {
		Page<Crew> crews = crewRepository.findAllByCrewTypeAndFirstNameLike(Constants.CREW_FLIGHT_CREW, firstName, new PageRequest(page, 10));
		return crews;
	}
	
	public Page<Crew> getPageFlightCrewByLastName(String lastName, int page) {
		Page<Crew> crews = crewRepository.findAllByCrewTypeAndLastNameLike(Constants.CREW_FLIGHT_CREW, lastName, new PageRequest(page, 10));
		return crews;
	}
	
	public Page<Crew> getPageFlightCrewByCompanyId(String companyId, int page) {
		Page<Crew> crews = crewRepository.findAllByCrewTypeAndCompanyIdLike(Constants.CREW_FLIGHT_CREW, companyId, new PageRequest(page, 10));
		return crews;
	}
	
	public Page<Crew> doAdvancedSearch (String type, String param, String value, int page) {
		Page<Crew> crews = null;
		if (type.equalsIgnoreCase(Constants.CREW_CABIN_CREW)) {
			if (param.equalsIgnoreCase("firstName")) {
				crews = getPageCabinCrewByFirstName(value, page);
			} else if (param.equalsIgnoreCase("lastName"))  {
				crews = getPageCabinCrewByLastName(value, page);
			} else if (param.equalsIgnoreCase("companyId")) {
				crews = getPageCabinCrewByCompanyId(value, page);
			}
		} else if (type.equalsIgnoreCase(Constants.CREW_FLIGHT_CREW)) {
			if (param.equalsIgnoreCase("firstName")) {
				crews = getPageFlightCrewByFirstName(value, page);
			} else if (param.equalsIgnoreCase("lastName"))  {
				crews = getPageFlightCrewByLastName(value, page);
			} else if (param.equalsIgnoreCase("companyId")) {
				crews = getPageFlightCrewByCompanyId(value, page);
			}
		} 
		return crews;
	}
	
	public Crew updateCrew (Crew crewToUpdate) {
		Crew crewInDB = crewRepository.findByUsername(crewToUpdate.getUsername());
		crewToUpdate.setAssignedRoutes(crewInDB.getAssignedRoutes());
		crewToUpdate.setPastRoutes(crewInDB.getPastRoutes());
		crewToUpdate.setPassword(crewInDB.getPassword());
		crewToUpdate.setId(crewInDB.getId());
		Crew updated = crewRepository.save(crewToUpdate);
		return updated;
	}
	
	public List<Crew> findAllByArrayIDs(ArrayList<String> ids) {
		ArrayList<ObjectId> idsToQuery = new ArrayList<>();
		for (String id : ids) {
			idsToQuery.add(new ObjectId(id));
		}
		return crewRepository.findAllById(idsToQuery);
	}

}
