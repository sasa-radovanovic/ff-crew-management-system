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

/**
 * 
 * Main Application Entry point
 * 
 * @author Sasa Radovanovic
 *
 */
@SpringBootApplication
public class CrewManagementSystemApplication implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(CrewManagementSystemApplication.class);

	@Autowired
	private AirplaneTypeRepository airplaneTypeRepository;

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
		
		logger.debug("--- Removing all data from collections...");

		airplaneTypeRepository.deleteAll();
		routeRepository.deleteAll();
		scheduledRouteRepository.deleteAll();
		airplaneRepository.deleteAll();
		crewRepository.deleteAll();

		logger.debug(">>> Data cleared");
		
		
		logger.debug("--- Adding airplane types...");
		airplaneTypeRepository.save(new AirplaneType("Airbus", "A320"));
		airplaneTypeRepository.save(new AirplaneType("Airbus", "A319"));
		airplaneTypeRepository.save(new AirplaneType("ATR", "72-500"));
		airplaneTypeRepository.save(new AirplaneType("ATR", "72-200"));
		airplaneTypeRepository.save(new AirplaneType("Boeing", "737-300"));
		airplaneTypeRepository.save(new AirplaneType("Airbus", "A330-200"));
		logger.debug(">>> Airplane types added");

		// Add A330 to the fleet
		logger.debug("--- Adding A330s");
		AirplaneType airplaneType_A330 = airplaneTypeRepository.findByType("A330-200");
		Airplane YU_ARA = new Airplane(airplaneType_A330, "YU-ARA", 254, "11/05/2016", "885");
		airplaneRepository.save(YU_ARA);

		// Add A320s to the fleet
		logger.debug("--- Adding A320s");
		AirplaneType airplaneType_A320 = airplaneTypeRepository.findByType("A320");
		Airplane YU_APG = new Airplane(airplaneType_A320, "YU-APG", 155, "11/03/2014", "2587");
		Airplane YU_APH = new Airplane(airplaneType_A320, "YU-APH", 155, "31/03/2014", "2645");
		airplaneRepository.save(YU_APG);
		airplaneRepository.save(YU_APH);
		
		// Add A319s to the fleet
		logger.debug("--- Adding A319s");
		AirplaneType airplaneType_A319 = airplaneTypeRepository.findByType("A319");
		Airplane YU_API = new Airplane(airplaneType_A319, "YU-API", 128, "28/10/2013", "1140");
		Airplane YU_APJ = new Airplane(airplaneType_A319, "YU-APJ", 128, "18/07/2014", "1159");
		Airplane YU_APA = new Airplane(airplaneType_A319, "YU-APA", 128, "06/03/2014", "2277");
		Airplane YU_APB = new Airplane(airplaneType_A319, "YU-APB", 128, "29/04/2014", "2296");
		Airplane YU_APD = new Airplane(airplaneType_A319, "YU-APD", 128, "28/08/2014", "2335");
		Airplane YU_APC = new Airplane(airplaneType_A319, "YU-APC", 128, "07/10/2013", "2621");
		Airplane YU_APE = new Airplane(airplaneType_A319, "YU-APE", 128, "01/11/2013", "3252");
		Airplane YU_APF = new Airplane(airplaneType_A319, "YU-APF", 128, "01/12/2013", "3317");
		airplaneRepository.save(YU_API);
		airplaneRepository.save(YU_APJ);
		airplaneRepository.save(YU_APA);
		airplaneRepository.save(YU_APB);
		airplaneRepository.save(YU_APD);
		airplaneRepository.save(YU_APC);
		airplaneRepository.save(YU_APE);
		airplaneRepository.save(YU_APF);
		
		// Add 737s to the fleet
		logger.debug("--- Adding 737s");
		AirplaneType airplaneType_B737 = airplaneTypeRepository.findByType("737-300");
		Airplane YU_AND = new Airplane(airplaneType_B737, "YU-AND", 134, "01/08/2013", "23329");
		Airplane YU_ANI = new Airplane(airplaneType_B737, "YU-ANI", 134, "01/08/2013", "23416");
		Airplane YU_ANK = new Airplane(airplaneType_B737, "YU-ANK", 134, "01/08/2013", "23715");
		airplaneRepository.save(YU_AND);
		airplaneRepository.save(YU_ANI);
		airplaneRepository.save(YU_ANK);
		
		
		// Add ATRs to the fleet
		logger.debug("--- Adding ATRs");
		AirplaneType airplaneType_ATR75 = airplaneTypeRepository.findByType("72-500");
		Airplane YU_ALU = new Airplane(airplaneType_ATR75, "YU-ALU", 66, "01/08/2013", "536");
		Airplane YU_ALT = new Airplane(airplaneType_ATR75, "YU-ALT", 66, "01/08/2013", "555");
		Airplane YU_ALV = new Airplane(airplaneType_ATR75, "YU-ALV", 66, "01/08/2013", "727");
		airplaneRepository.save(YU_ALU);
		airplaneRepository.save(YU_ALT);
		airplaneRepository.save(YU_ALV);
		AirplaneType airplaneType_ATR72 = airplaneTypeRepository.findByType("72-200");
		Airplane YU_ALN = new Airplane(airplaneType_ATR72, "YU-ALN", 66, "01/08/2013", "180");
		Airplane YU_ALO = new Airplane(airplaneType_ATR72, "YU-ALO", 66, "01/08/2013", "186");
		Airplane YU_ALP = new Airplane(airplaneType_ATR72, "YU-ALP", 66, "01/08/2013", "189");
		airplaneRepository.save(YU_ALN);
		airplaneRepository.save(YU_ALO);
		airplaneRepository.save(YU_ALP);


		logger.debug(">>> Airplanes added");

		logger.debug("--- Adding Monday, Wednesday and Friday Scheduled Route to JFK");
		ArrayList<Integer> daysOfTheWeek = new ArrayList<>();
		daysOfTheWeek.add(1);
		daysOfTheWeek.add(3);
		daysOfTheWeek.add(5);
		ScheduledRoute scheduledRoute = new ScheduledRoute("Summer work days to JFK", 
				airplaneType_A330, daysOfTheWeek, "BEG", "JFK", "10:00", "16:00", 10.00);

		scheduledRouteRepository.save(scheduledRoute);
		logger.debug(">>> Scheduled route added");

		
		logger.debug("--- Adding specific flights [one to JFK and one to BKK]");
		Airplane airplaneInDB = airplaneRepository.findByRegistrationLike("YU-ARA");

		HashMap<String, ArrayList<String>> crewsMap = new HashMap<String, ArrayList<String>>();
		crewsMap.put(Constants.CREW_FLIGHT_CREW, new ArrayList<>());
		crewsMap.put(Constants.CREW_CABIN_CREW, new ArrayList<>());
		Route route = new Route(airplaneInDB.getId(), 
				"BEG", "JFK", 10.00, crewsMap, "09/01/2017 10:00", "16:00", "JU500");

		Route route2 = new Route(airplaneInDB.getId(), 
				"BEG", "BKK", 9.50, crewsMap, "10/01/2017 08:00", "17:30", "JU550");

		routeRepository.save(route);
		routeRepository.save(route2);

		logger.debug(">>> Routes added");


		logger.debug("--- Adding dummy crew to DB");
		Crew crew1 = new Crew("Sasa", "Radovanovic", "rsasa", "airserbia", "sasa1kg@yahoo.com", 
				true, new ArrayList<CrewRoute>(), Constants.CREW_FLIGHT_CREW, new ArrayList<String>(), "RS", "00001001");

		Crew crew2 = new Crew("Sasa 1", "Radovanovic 2", "admin", "admin", "admin@yahoo.com", 
				true, new ArrayList<CrewRoute>(), Constants.CREW_ADMIN, new ArrayList<String>(), "RS", "00001002");

		Crew crew3 = new Crew("Sasa3", "Radovanovic", "rsasa3", "airserbia", "sasa1kg3@yahoo.com", 
				true, new ArrayList<CrewRoute>(), Constants.CREW_CABIN_CREW, new ArrayList<String>(), "RS", "00001003");

		Crew crew4 = new Crew("Sasa 4", "Radovanovic 2", "rsasa4", "airserbia", "admin4@yahoo.com", 
				true, new ArrayList<CrewRoute>(), Constants.CREW_CABIN_CREW, new ArrayList<String>(), "RS", "00001004");

		Crew crew5 = new Crew("Sasa 5", "Radovanovic", "rsasa5", "airserbia", "sasa1kg4@yahoo.com", 
				true, new ArrayList<CrewRoute>(), Constants.CREW_CABIN_CREW, new ArrayList<String>(), "RS", "00001005");

		Crew crew6 = new Crew("Sasa 6", "Radovanovic 2", "rsasa6", "airserbia", "admin5@yahoo.com", 
				true, new ArrayList<CrewRoute>(), Constants.CREW_CABIN_CREW, new ArrayList<String>(), "RS", "00001006");

		Crew crew7 = new Crew("Sasa 7", "Radovanovic", "rsasa7", "airserbia", "sasa1kg6@yahoo.com", 
				true, new ArrayList<CrewRoute>(), Constants.CREW_CABIN_CREW, new ArrayList<String>(), "RS", "00001007");

		Crew crew8 = new Crew("Sasa 8", "Radovanovic 2", "rsasa8", "airserbia", "admin7@yahoo.com", 
				true, new ArrayList<CrewRoute>(), Constants.CREW_CABIN_CREW, new ArrayList<String>(), "RS", "00001008");

		crewService.createCrew(crew1);
		crewService.createCrew(crew2);
		crewService.createCrew(crew3);
		crewService.createCrew(crew4);
		crewService.createCrew(crew5);
		crewService.createCrew(crew6);
		crewService.createCrew(crew7);
		crewService.createCrew(crew8);

		logger.debug(">>> Crew added");


		logger.debug("--- Test crew update");

		Page<Crew> pageC = crewRepository.findAllByCrewType(Constants.CREW_CABIN_CREW, new PageRequest(0, 10));

		for (Crew crew : pageC) {
			Crew crewToUpd = crew;
			Route routeFromRepo = routeRepository.findByDeparture("BEG").get(0);

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
		
		logger.debug(">>> Crew updated");

		logger.info("-------------------------------------");
		logger.info("--- CMS SET");
		logger.info("-------------------------------------");

	}
}
