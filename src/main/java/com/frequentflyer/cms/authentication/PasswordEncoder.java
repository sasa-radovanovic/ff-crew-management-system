package com.frequentflyer.cms.authentication;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {
	
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
	    return new BCryptPasswordEncoder();
	}
	
}
