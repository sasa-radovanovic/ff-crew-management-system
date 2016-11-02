package com.frequentflyer.cms;

import org.springframework.security.core.GrantedAuthority;

public class PrincipalChecker {

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
