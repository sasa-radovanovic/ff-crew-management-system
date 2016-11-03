package com.frequentflyer.cms.repositories;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.frequentflyer.cms.models.Crew;


/**
 * 
 * Repository for handing crew operations
 * 
 * @author Sasa Radovanovic
 *
 */
public interface CrewRepository extends MongoRepository<Crew, String> {

	List<Crew> findByFirstNameLike(String name);
	
	List<Crew> findByLastNameLike(String lastName);
	
	List<Crew> findByCrewType(String crewType);
	
	List<Crew> findByCountryOfOriginLike(String countryOfOrigin);
	
	Crew findByUsername(String username);
	
	Page<Crew> findAllByCrewType(String type, Pageable pageable);
	
	Page<Crew> findAllByCrewTypeAndFirstNameLike(String type, String firstName, Pageable pageable);
	
	Page<Crew> findAllByCrewTypeAndLastNameLike(String type, String lastName, Pageable pageable);
	
	Page<Crew> findAllByCrewTypeAndCompanyIdLike(String type, String companyId, Pageable pageable);
	
	long countByCrewType(String type);

	Crew findById (String id);
	
	@Query(value = "{'_id' : { $in : ?0 }}")
	List<Crew> findAllById (ArrayList<ObjectId> ids);
}
