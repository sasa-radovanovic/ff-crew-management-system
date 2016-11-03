package com.frequentflyer.cms.rest_controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * Rest endpoint to handle general application operations
 * 
 * @author Sasa Radovanovic
 *
 */
@RestController
public class GeneralApplicationRestCtrl {
	
	Logger logger = LoggerFactory.getLogger(GeneralApplicationRestCtrl.class);
	
	@RequestMapping(value="/invalidate",
			method = RequestMethod.POST)
	public HttpStatus invalidateSession(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("I got here...");
		HttpSession session = request.getSession(false);
		if(request.isRequestedSessionIdValid() && session != null) {
			logger.info(" invalidateSession | Session invalidation...");
			SecurityContextHolder.clearContext();
			session.invalidate();
		} else {
			return HttpStatus.NOT_FOUND;
		}
		return HttpStatus.OK;
	}
	
	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

}
