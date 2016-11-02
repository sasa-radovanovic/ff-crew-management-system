package com.frequentflyer.cms.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.frequentflyer.cms.Constants;
import com.frequentflyer.cms.models.Crew;
import com.frequentflyer.cms.models.Route;
import com.frequentflyer.cms.models.ScheduledRoute;
import com.frequentflyer.cms.models.helpers.CrewRoute;
import com.frequentflyer.cms.models.helpers.SortedScheduledRoutes;
import com.frequentflyer.cms.repositories.AirplaneTypeRepository;
import com.frequentflyer.cms.repositories.CrewRepository;
import com.frequentflyer.cms.repositories.RouteRepository;
import com.frequentflyer.cms.repositories.ScheduledRouteRepository;

@Service
public class RouteService {
	
	Logger logger = LoggerFactory.getLogger(RouteService.class);
	
	@Autowired
	AirplaneTypeRepository airplaneTypeRepository;
	
	@Autowired
	RouteRepository routeRepository;
	
	@Autowired
	ScheduledRouteRepository scheduledRouteRepository;
	
	@Autowired
	CrewRepository crewRepository;
	
	public List<Route> findRouteByDepartureAirport (String departure) {
		List<Route> routes = new ArrayList<>();
		routes = routeRepository.findByDeparture(departure);
		return routes;
	}
	
	public Route findRouteById (String id) {
		logger.info(" findRouteById | id " + id);
		Route route = routeRepository.findById(id);
		return route;
	}
	
	public ScheduledRoute saveNewScheduledRoute (ScheduledRoute scheduledRoute) {
		ScheduledRoute savedRoute = scheduledRouteRepository.save(scheduledRoute);
		return savedRoute;
	}
	
	public ScheduledRoute retrieveScheduledRouteById (String id) {
		ScheduledRoute route = scheduledRouteRepository.findById(id);
		return route;
	}
	
	public ScheduledRoute updateScheduledRoute (ScheduledRoute scheduledRoute) {
		ScheduledRoute route = scheduledRouteRepository.save(scheduledRoute);
		return route;
	}
	
	public Route updateRoute (Route route) {
		Route routeUpdated = routeRepository.save(route);
		return routeUpdated;
	}

	public SortedScheduledRoutes retrieveSortedScheduledRoutes() {
		SortedScheduledRoutes sortedRoutes = new SortedScheduledRoutes();
		List<ScheduledRoute> scheduledRoutes = new ArrayList<ScheduledRoute>();
		scheduledRoutes = scheduledRouteRepository.findAll();
		if (scheduledRoutes == null || scheduledRoutes.size() == 0) {
			return sortedRoutes;
		}
		for (ScheduledRoute scheduledRoute : scheduledRoutes) {
			ArrayList<Integer> days = scheduledRoute.getDaysOfTheWeek();
			for (Integer day : days) {
				sortedRoutes.insertRoute(day, scheduledRoute);
			}
		}
		return sortedRoutes;
	}
	
	public void removeScheduledRoute (String routeId) {
		scheduledRouteRepository.delete(routeId);
	}
	
	
	public Page<Route> getPageRoutes(int page) {
		Page<Route> routes = routeRepository.findAll(new PageRequest(page, 10));
		return routes;
	}
	
	public Route createRoute (Route route) {
		Route createRoute = routeRepository.save(route);
		return createRoute;
	}

	public Route removeRoute (String routeId) {
		Route route = routeRepository.findById(routeId);
		HashMap<String, ArrayList<String>> crews = route.getAssignedCrew();
		if (crews.containsKey(Constants.CREW_FLIGHT_CREW)) {
			ArrayList<String> crewIds = crews.get(Constants.CREW_FLIGHT_CREW);
			for (String crewId : crewIds) {
				Crew crew = crewRepository.findById(crewId);
				ArrayList<CrewRoute> crewRoutes = crew.getAssignedRoutes();
				for (CrewRoute crewRoute : crewRoutes) {
					if (crewRoute.getRouteId().equalsIgnoreCase(routeId)) {
						crewRoutes.remove(crewRoute);
					}
				}
				crewRepository.save(crew);
			}
		}
		if (crews.containsKey(Constants.CREW_CABIN_CREW)) {
			ArrayList<String> crewIds = crews.get(Constants.CREW_CABIN_CREW);
			for (String crewId : crewIds) {
				Crew crew = crewRepository.findById(crewId);
				ArrayList<CrewRoute> crewRoutes = crew.getAssignedRoutes();
				for (CrewRoute crewRoute : crewRoutes) {
					if (crewRoute.getRouteId().equalsIgnoreCase(routeId)) {
						crewRoutes.remove(crewRoute);
					}
				}
				crewRepository.save(crew);
			}
		}
		routeRepository.delete(route);
		return route;
	}
	
	public Crew assignCrewToRoute (String crewType, String crewId, String routeId) throws Exception {
		Route route = routeRepository.findById(routeId);
		if (route == null) {
			throw new Exception("NOT_FOUND");
		}
		Crew crew = crewRepository.findById(crewId);
		if (crew == null) {
			throw new Exception("NOT_FOUND");
		}
		HashMap<String, ArrayList<String>> crewAssigned = route.getAssignedCrew();
		if (crewAssigned.containsKey(crewType)) {
			ArrayList<String> crews = crewAssigned.get(crewType);
			crews.add(crewId);
			crewAssigned.replace(crewType, crews);
		} else {
			ArrayList<String> crews = new ArrayList<>();
			crews.add(crewId);
			crewAssigned.put(crewType, crews);
		}
		route.setAssignedCrew(crewAssigned);
		routeRepository.save(route);
		ArrayList<CrewRoute> crewRoutes = crew.getAssignedRoutes();
		CrewRoute newRoute = new CrewRoute(route.getId(), 
				route.getLocalDepartureTime(), route.getLength(), 
				route.getDeparture(), route.getArrival(), route.getFlightNumber());
		crewRoutes.add(newRoute);
		crew.setAssignedRoutes(crewRoutes);
		Crew assignedCrew = crewRepository.save(crew);
		return assignedCrew;
	}

}
