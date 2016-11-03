package com.frequentflyer.cms;

import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * Principal Checker for quick preauthorization
 * 
 * @author Sasa Radovanovic
 *
 */
public class PrincipalChecker {

	/**
	 * 
	 * Verify that caller is admin
	 * 
	 * @param principal
	 * @return if principal is admin
	 */
	public static boolean isAdmin(Object principal) {
		if(principal instanceof org.springframework.security.core.userdetails.UserDetails) {
			for (GrantedAuthority auth : ((org.springframework.security.core.userdetails.UserDetails) principal).getAuthorities()) {
				if (auth.getAuthority().equalsIgnoreCase(Constants.CREW_ADMIN)) {
					return true;
				}
			}
		} else {
			return false;
		}
		return false;
	}
	
	/**
	 * 
	 * Verify that given resource can be accessed only by admin and that user
	 * 
	 * @param username - username of caller
	 * @param principal - Principal
	 * @return if principal is admin or caller is user
	 */
	public static final boolean isAdminOrThisUser(String username, Object principal) {
		if(principal instanceof org.springframework.security.core.userdetails.UserDetails) {
			for (GrantedAuthority auth : ((org.springframework.security.core.userdetails.UserDetails) principal).getAuthorities()) {
				if (auth.getAuthority().equalsIgnoreCase(Constants.CREW_ADMIN)
						|| (((org.springframework.security.core.userdetails.UserDetails) principal).getUsername().equalsIgnoreCase(username))) {
					return true;
				}
			}
		} else {
			return false;
		}
		return false;
	}

}
