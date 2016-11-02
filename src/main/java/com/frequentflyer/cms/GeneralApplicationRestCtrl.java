package com.frequentflyer.cms;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HttpServletBean;

import com.frequentflyer.cms.models.Crew;

@RestController
public class GeneralApplicationRestCtrl {
	
	Logger logger = LoggerFactory.getLogger(GeneralApplicationRestCtrl.class);
	
	@RequestMapping(value="/invalidate",
			method = RequestMethod.POST)
	public ResponseEntity<Crew> invalidateSession(HttpServletRequest request, HttpServletBean response) {
		HttpSession session = request.getSession(false);
		if(request.isRequestedSessionIdValid() && session != null) {
			logger.info(" invalidateSession | Session invalidation...");
			SecurityContextHolder.clearContext();
			session.invalidate();
		} else {
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

}
