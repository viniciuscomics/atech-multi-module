package com.atech.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.atech.dto.FlightPageable;
import com.atech.model.Flight;
import com.atech.service.impl.FlightService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@Api(value = "Register and search for flights", tags = "Flights",
authorizations = {@Authorization(value="basicAuth")})
@RestController
@RequestMapping(value="/flight")
public class FlightResource {

	private static final String ROLE_ADMIN = "ROLE_ADMIN";
	
	@Autowired
	private FlightService flightService;
	
	@ApiOperation(value ="Searches for a flight by id.", response = Flight.class,code=200)
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Flight getById(@PathVariable Long id) {
		return flightService.findById(id);
	}
	
	@ApiOperation(value ="Search all registered flights and return as a pageable", response = FlightPageable.class,code=200)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public FlightPageable getAll(@PageableDefault(page = 0, size =3)Pageable pageable){
		return flightService.findAll(pageable);
	}
	
	@ApiOperation(value = "Register a new flight.",response = Flight.class,
	notes = "This resource saves a new flight and if one of your relationships is not registered, it will be saved.",
	nickname = "create")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('"+ROLE_ADMIN+"')")
	public Flight create(@Valid @RequestBody Flight flight) {
		
		return flightService.save(flight);
	}
}
