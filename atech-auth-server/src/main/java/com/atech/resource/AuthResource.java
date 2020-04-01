package com.atech.resource;

import java.security.Principal;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Api(value = "API to validate the access token", tags = "user",
authorizations = {@Authorization(value="basicAuth")})
public class AuthResource { 
	
	@RequestMapping("/user")
    public Principal getCurrentLoggedInUser(Principal user) {		
        return user;
    }
}
