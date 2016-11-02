package com.frequentflyer.cms;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.frequentflyer.cms.models.Airplane;
import com.frequentflyer.cms.models.AirplaneType;
import com.frequentflyer.cms.models.Crew;
import com.frequentflyer.cms.models.Route;
import com.frequentflyer.cms.models.ScheduledRoute;
import com.frequentflyer.cms.models.helpers.CrewRoute;
import com.frequentflyer.cms.repositories.AirplaneRepository;
import com.frequentflyer.cms.repositories.AirplaneTypeRepository;
import com.frequentflyer.cms.repositories.CrewRepository;
import com.frequentflyer.cms.repositories.RouteRepository;
import com.frequentflyer.cms.repositories.ScheduledRouteRepository;
import com.frequentflyer.cms.services.CrewService;


@SpringBootApplication
public class CrewManagementSystemApplication implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(CrewManagementSystemApplication.class);

	@Autowired
	private AirplaneTypeRepository repository;

	@Autowired
	private RouteRepository routeRepository;

	@Autowired
	private AirplaneRepository airplaneRepository;

	@Autowired
	private ScheduledRouteRepository scheduledRouteRepository;

	@Autowired
	private CrewRepository crewRepository;

	@Autowired
	private CrewService crewService;


	public static void main(String[] args) {
		SpringApplication.run(CrewManagementSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		repository.deleteAll();

		// save a couple of customers
		repository.save(new AirplaneType("Airbus", "A380"));
		repository.save(new AirplaneType("Boeing", "747-800"));

		// fetch all customers
		System.out.println("Airplane Type found with findAll():");
		System.out.println("-------------------------------");
		for (AirplaneType airplane : repository.findAll()) {
			System.out.println(airplane);
		}
		System.out.println();

		// fetch an individual customer
		System.out.println("Airplane Type found with findByManufacturer('Airbus'):");
		System.out.println("--------------------------------");
		for (AirplaneType airplane : repository.findByManufacturer("Airbus")) {
			System.out.println(airplane);
		}


		System.out.println("Airplane Type found with findByManufacturer('Airbus'):");
		System.out.println("--------------------------------");
		System.out.println(repository.findByType("747-800"));

		System.out.println("DATABASE TEST");
		System.out.println("--------------------------------");
		routeRepository.deleteAll();
		scheduledRouteRepository.deleteAll();
		airplaneRepository.deleteAll();
		crewRepository.deleteAll();

		AirplaneType airplaneType = repository.findByType("A380");

		Airplane airplane = new Airplane(airplaneType, "A6-EEC", 580, "05/12/2012", "MSN1250");
		Airplane airplane2 = new Airplane(airplaneType, "A6-EEF", 580, "10/10/2008", "MSN810");

		AirplaneType airplaneType2 = repository.findByType("747-800");

		Airplane airplane3 = new Airplane(airplaneType2, "A6-AAB", 495, "05/12/2012", "MSN1259");
		Airplane airplane4 = new Airplane(airplaneType2, "A6-AA7", 495, "10/10/2008", "MSN8107");

		airplaneRepository.save(airplane);
		airplaneRepository.save(airplane2);
		airplaneRepository.save(airplane3);
		airplaneRepository.save(airplane4);

		logger.info("----------- SAVED AIRPLANES");

		logger.info("1 : " + airplaneRepository.findByRegistrationLike("A6-EEC").toString());

		logger.info("2 : " + airplaneRepository.findByRegistrationLike("A6-EEF").toString());

		logger.info("----------------------------");

		ArrayList<Integer> daysOfTheWeek = new ArrayList<>();
		daysOfTheWeek.add(1);
		daysOfTheWeek.add(5);
		ScheduledRoute scheduledRoute = new ScheduledRoute("Summer Service to SYD", 
				airplaneType, daysOfTheWeek, "BEG", "SYD", "10:00", "21:00", 14.50);

		scheduledRouteRepository.save(scheduledRoute);

		logger.info("----------- SAVED SCHEDULED ROUTE");

		logger.info("1 : " + scheduledRouteRepository.findByNameLike("SYD").get(0).toString());

		logger.info("----------------------------");

		Airplane airplaneInDB = airplaneRepository.findByRegistrationLike("A6-EEC");

		HashMap<String, ArrayList<String>> crewsMap = new HashMap<String, ArrayList<String>>();
		crewsMap.put(Constants.CREW_FLIGHT_CREW, new ArrayList<>());
		crewsMap.put(Constants.CREW_CABIN_CREW, new ArrayList<>());
		Route route = new Route(airplaneInDB.getId(), 
				"DXB", "SYD", 14.50, crewsMap, "10/10/2016 10:00", "21:00", "EK612");

		Route route2 = new Route(airplaneInDB.getId(), 
				"DXB", "HKG", 8.50, crewsMap, "10/10/2016 09:00", "17:30", "EK112");

		routeRepository.save(route);

		routeRepository.save(route2);

		logger.info("----------- SAVED ROUTE");

		logger.info("1 : " + routeRepository.findByDeparture("DXB").get(0).toString());

		logger.info("2 : " + routeRepository.findByDeparture("DXB").get(1).toString());

		logger.info("----------------------------");

		Crew crew1 = new Crew("Sasa", "Radovanovic", "rsasa", "fiorentina2", "sasa1kg@yahoo.com", 
				true, new ArrayList<CrewRoute>(), Constants.CREW_FLIGHT_CREW, new ArrayList<String>(), "RS", "00001001");

		Crew crew2 = new Crew("Sasa 1", "Radovanovic 2", "admin", "admin", "admin@yahoo.com", 
				true, new ArrayList<CrewRoute>(), Constants.CREW_ADMIN, new ArrayList<String>(), "RS", "00001002");

		Crew crew3 = new Crew("Sasa3", "Radovanovic", "rsasa3", "fiorentina2", "sasa1kg3@yahoo.com", 
				true, new ArrayList<CrewRoute>(), Constants.CREW_CABIN_CREW, new ArrayList<String>(), "RS", "00001001");

		Crew crew4 = new Crew("Sasa 4", "Radovanovic 2", "rsasa4", "admin", "admin4@yahoo.com", 
				true, new ArrayList<CrewRoute>(), Constants.CREW_CABIN_CREW, new ArrayList<String>(), "RS", "00001002");

		Crew crew5 = new Crew("Sasa 5", "Radovanovic", "rsasa5", "fiorentina2", "sasa1kg4@yahoo.com", 
				true, new ArrayList<CrewRoute>(), Constants.CREW_CABIN_CREW, new ArrayList<String>(), "RS", "00001001");

		Crew crew6 = new Crew("Sasa 6", "Radovanovic 2", "rsasa6", "admin", "admin5@yahoo.com", 
				true, new ArrayList<CrewRoute>(), Constants.CREW_CABIN_CREW, new ArrayList<String>(), "RS", "00001002");

		Crew crew7 = new Crew("Sasa 7", "Radovanovic", "rsasa7", "fiorentina2", "sasa1kg6@yahoo.com", 
				true, new ArrayList<CrewRoute>(), Constants.CREW_CABIN_CREW, new ArrayList<String>(), "RS", "00001001");

		Crew crew8 = new Crew("Sasa 8", "Radovanovic 2", "rsasa8", "admin", "admin7@yahoo.com", 
				true, new ArrayList<CrewRoute>(), Constants.CREW_CABIN_CREW, new ArrayList<String>(), "RS", "00001002");


		crewService.createCrew(crew1);

		crewService.createCrew(crew2);

		crewService.createCrew(crew3);

		crewService.createCrew(crew4);

		crewService.createCrew(crew5);

		crewService.createCrew(crew6);

		crewService.createCrew(crew7);

		crewService.createCrew(crew8);

		logger.info("----------- SAVED CREW");

		logger.info("1 : " + crewRepository.findByFirstNameLike("Sasa").get(0).toString());

		logger.info("2 : " + crewRepository.findByFirstNameLike("Sasa").get(1).toString());

		logger.info("----------------------------");

		logger.info("----------- UPDATE CREW");

		Page<Crew> pageC = crewRepository.findAllByCrewType(Constants.CREW_CABIN_CREW, new PageRequest(0, 10));


		for (Crew crew : pageC) {
			Crew crewToUpd = crew;
			logger.info("Assign crew " + crew.getId());

			Route routeFromRepo = routeRepository.findByDeparture("DXB").get(0);

			CrewRoute crewRoute = new CrewRoute(routeFromRepo.getId(), 
					routeFromRepo.getLocalDepartureTime(), 
					routeFromRepo.getLength(), 
					routeFromRepo.getDeparture(), 
					routeFromRepo.getArrival(),
					routeFromRepo.getFlightNumber());

			crewToUpd.getAssignedRoutes().add(crewRoute);
			crewToUpd.getLanguagesSpoken().add("rs");
			crewRepository.save(crewToUpd);

			HashMap<String, ArrayList<String>> users = routeFromRepo.getAssignedCrew();
			if (users.containsKey(crewToUpd.getCrewType())) {
				ArrayList<String> userIds = routeFromRepo.getAssignedCrew().get(crewToUpd.getCrewType());
				userIds.add(crewToUpd.getId());
				routeFromRepo.getAssignedCrew().put(crewToUpd.getCrewType(), userIds);
			} else {
				ArrayList<String> userIds = new ArrayList<String>();
				userIds.add(crewToUpd.getId());
				routeFromRepo.getAssignedCrew().put(crewToUpd.getCrewType(), userIds);
			}
			routeRepository.save(routeFromRepo);

		}

		logger.info("1 : " + crewRepository.findByFirstNameLike("Sasa").get(0).toString());

		logger.info("----------------------------");

		logger.info("------------ PAGING THE CREW");

		Page<Crew> crews = crewService.getPageCabinCrew(0);

		int i = 1;
		logger.info("PAGE 1");
		for (Crew crew : crews) {
			logger.info(i + " >>>>> " +  crew.getUsername());
			i++;
		}

		Page<Crew> crews2 = crewService.getPageCabinCrew(1);
		int j = 1;
		logger.info("PAGE 2");
		for (Crew crew : crews2) {
			logger.info(j + " >>>>> " +  crew.getUsername());
			j++;
		}

		logger.info("SIZE CABIN CREW: " + crewService.getCrewSize(Constants.CREW_CABIN_CREW));
		logger.info("SIZE FLIGHT CREW: " + crewService.getCrewSize(Constants.CREW_FLIGHT_CREW));

		logger.info("----------------------------");

		logger.info("----------------------------");
		logger.info("------------ PAGING THE CREW ADVANCED");

		Page<Crew> crewsAdv = crewService.getPageCabinCrewByFirstName("Sasa", 0);

		logger.info("Number of elements " + crewsAdv.getNumberOfElements());

		logger.info("Total elements " + crewsAdv.getTotalElements());

		logger.info("Total pages " + crewsAdv.getTotalPages());

		int t = 1;
		logger.info("PAGE 1");
		for (Crew crew : crewsAdv) {
			logger.info(t + " >>>>> " +  crew.getUsername());
			t++;
		}

		logger.info("----------------------------");

		logger.info("----------------------------");
		logger.info("----------------------------");
		logger.info("   Find by IDs in the list   ");

		Crew crew_0 = crewRepository.findByFirstNameLike("Sasa").get(0);
		Crew crew_1 = crewRepository.findByFirstNameLike("Sasa").get(1);

		logger.info("Add search " + crew_0.getId());
		logger.info("Add search " + crew_1.getId());
		logger.info("----------------------------");

		ArrayList<String> crewIds = new ArrayList<>();
		crewIds.add(crew_0.getId());
		crewIds.add(crew_1.getId());

		ArrayList<Crew> crewList = (ArrayList<Crew>) crewService.findAllByArrayIDs(crewIds);

		int p = 1;
		for (Crew crew : crewList) {
			logger.info("FOUND " + p + " --- " + crew.getFirstName() + " " + crew.getId());
			p++;
		}


		logger.info("----------------------------");
		logger.info("----------------------------");
		//routeRepository.save(new Route("BEG", "JFK", airplaneType, 6.5));

	}
}
