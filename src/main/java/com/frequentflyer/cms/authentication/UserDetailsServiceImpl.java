package com.frequentflyer.cms.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.frequentflyer.cms.models.Crew;
import com.frequentflyer.cms.services.CrewService;

/**
 * 
 * User Details service implementation
 * 
 * @author Sasa Radovanovic 
 *
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	CrewService crewService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Crew account = crewService.retrieveUserByUsername(username);
		if (account == null) {
			throw new UsernameNotFoundException("No user found");
		}
		return new AccountUserDetails(account);
	}

}