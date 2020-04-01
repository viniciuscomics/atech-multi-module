package com.atech.service.impl;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import com.atech.dto.FlightPageable;
import com.atech.exception.CitySameInvalidException;
import com.atech.exception.DepartureInvalidException;
import com.atech.exception.FlightNotFoundException;
import com.atech.exception.PilotInvalidException;
import com.atech.exception.StatusFlightInvalidException;
import com.atech.model.Flight;
import com.atech.model.StatusFlight;
import com.atech.repository.FlightRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FlightService {
	
	private HttpServletRequest httpServletRequest;
	
	private FlightRepository flightRepository;

	public FlightService(@Autowired FlightRepository flightRepository,
			@Autowired  HttpServletRequest httpServletRequest) {
		
		this.flightRepository = flightRepository;
		this.httpServletRequest = httpServletRequest;
	}
	
	public Flight findById(Long id) {
		
		log.info("Find by id -> ", id);
		
		Optional<Flight> opt = flightRepository.findById(id);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		
		throw new FlightNotFoundException();
	}

	public FlightPageable findAll(Pageable pageable) {		
		
		log.info("Find by all -> ", pageable);
		Page<Flight> pagesFlight = flightRepository.findAll(pageable);
		
		PageImpl<Flight> page = new PageImpl<>(pagesFlight.getContent(), pagesFlight.getPageable(), pagesFlight.getTotalElements());	
		
		return createHateoas(page);
	}	

	public Flight update(Long id, Flight flight) {
		log.info("Update flight ", id);
		flight.setId(id);
		
		Optional<Flight> opt = flightRepository.findById(id);
		
		if(opt.isPresent()) {	
			validateFlightAll(flight);
			return flightRepository.save(flight);
		}
		log.error("", "Error on update flight",flight);
		throw new FlightNotFoundException();	
		
	}
	
	public Flight save(final Flight flight) {
		
		log.info("Saving flight ",flight);
		
		if(!flight.getStatus().name().equals(StatusFlight.READY.name())) {
			log.error("", "Error on save flight", flight);
			throw new StatusFlightInvalidException();
		}
		
		validateFlightAll(flight);;

		return flightRepository.save(flight);

	}
	
	private FlightPageable createHateoas(PageImpl<Flight> page) {

		FlightPageable listFlight = new FlightPageable();
		
		int numPage = page.getNumber();
		int totalPage = page.getTotalPages();

		listFlight.setListFlight(page.getContent());		
		
		String urlApiGateway = httpServletRequest.getHeader("X-Forwarded-Host")+httpServletRequest.getHeader("X-Forwarded-Prefix");
		
		log.info("Url api gateway "+urlApiGateway);
		
		if (page.getNumber() > 1) {
			listFlight.add(
					new Link(urlApiGateway + "?page=" + (numPage) + "&size=" + totalPage,
							"previous"));
		}
		listFlight
				.add(new Link(urlApiGateway + "?page=" + (numPage) + "&size=" + totalPage));

		if ((numPage + 1) < totalPage) {
			listFlight.add(new Link(
					urlApiGateway + "?page=" + (numPage + 1) + "&size=" + totalPage, "next"));
		}

		listFlight.add(new Link(
				urlApiGateway + "?page=" + (totalPage-1) + "&size=" + totalPage, "last"));
		
		listFlight.setPageNumber(numPage);
		listFlight.setPageSize(page.getNumberOfElements());
		listFlight.setTotalPage(totalPage);
		listFlight.setTotalCount((int) page.getTotalElements());

		return listFlight;
	}
	
	private void validateFlightAll(Flight flight) {		

		if (flight.getCitySource().equals(flight.getCityDestiny())) {
			log.error("","Error: city source and destination cannot be same",flight);
			throw new CitySameInvalidException();
		}
		
		if (flight.getDeparture().compareTo(flight.getArrive()) > 0) {
			log.error("","Error: departure cannot be greater arrive",flight);
			throw new DepartureInvalidException();
		}

		Optional<Flight> opt = flightRepository.verifyPilotBusy(
				flight.getDeparture(), flight.getArrive(),
				flight.getPilot().getId(), flight.getStatus());
		
				opt.ifPresent(e -> {
					log.error("","Error: pilot is in other flight",flight);
					throw new PilotInvalidException();
				});
	}
}
