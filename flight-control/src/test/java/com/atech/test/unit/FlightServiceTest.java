package com.atech.test.unit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.atech.dto.FlightPageable;
import com.atech.exception.CitySameInvalidException;
import com.atech.exception.DepartureInvalidException;
import com.atech.exception.FlightNotFoundException;
import com.atech.exception.PilotInvalidException;
import com.atech.exception.StatusFlightInvalidException;
import com.atech.model.Airplane;
import com.atech.model.City;
import com.atech.model.Flight;
import com.atech.model.Pilot;
import com.atech.model.StatusFlight;
import com.atech.repository.FlightRepository;
import com.atech.service.impl.FlightService;

public class FlightServiceTest {

	private FlightService flghtService;

	@Mock
	private FlightRepository flightMock;
	
	@Mock
	private HttpServletRequest httpServletRequest;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		flghtService = new FlightService(flightMock,httpServletRequest);
	}

	private Flight getNewFlight() {
		LocalDateTime departure = LocalDateTime.now();
		LocalDateTime arrive = LocalDateTime.now().plusDays(1l);

		Pilot pilot = new Pilot();
		pilot.setAge(50);
		pilot.setDocument("123456789");
		pilot.setName("Gustavo");
		pilot.setId(10l);

		City citySource = new City();
		citySource.setId(1l);
		citySource.setName("Rio de Janeiro");
		citySource.setNameCountry("Brasil");

		City cityDestiny = new City();
		cityDestiny.setId(1l);
		cityDestiny.setName("Sao Paulo-SP");
		cityDestiny.setNameCountry("Brasil");

		Airplane airplane = new Airplane();
		airplane.setId(2l);
		airplane.setModel("F12");
		airplane.setManufactur("Embraer");
		airplane.setManufacturingYear(2010);

		Flight flight = new Flight();
		flight.setId(15l);
		flight.setPilot(pilot);
		flight.setAirplane(airplane);
		flight.setArrive(arrive);
		flight.setDeparture(departure);
		flight.setCitySource(citySource);
		flight.setCityDestiny(cityDestiny);
		flight.setStatus(StatusFlight.READY);

		return flight;
	}	
	
	@Test
	public void find_all_sucess() {
		
		List<Flight> list = new ArrayList<>();
		
		Flight ad1 = getNewFlight();
		Flight ad2 = getNewFlight();
		Flight ad3 = getNewFlight();		
		ad2.setId(123l);
		ad3.setId(124l);
		
		list.add(ad1);
		list.add(ad2);
		list.add(ad3);		
		
		Page<Flight> page = new PageImpl<>(list);		
		
		when(flightMock.findAll(PageRequest.of(0, 2))).thenReturn(page);
		
		FlightPageable pageBase = flghtService.findAll(PageRequest.of(0, 2));
		
		assertThat(pageBase.getTotalCount(), equalTo(3));
		
	}
	
	@Test
	public void find_by_id_flight_sucess() {
		
		Flight flight = getNewFlight();		
		when(flightMock.findById(15L)).thenReturn(Optional.of(flight));	
		
		Flight ret = flghtService.findById(15l);	
		
		assertThat(ret.getId(), equalTo(15l));
		
	}
	
	@Test(expected = FlightNotFoundException.class)
	public void find_by_id_flight_exception() {			
		when(flightMock.findById(15L)).thenReturn(Optional.empty());		
		flghtService.findById(15l);		
	}
	
	@Test(expected = FlightNotFoundException.class)
	public void update_flight_exception() {
		
		Flight flight = getNewFlight();		
		when(flightMock.findById(15L)).thenReturn(Optional.empty());
		
		Flight newFlight = new Flight();
		newFlight.setId(15l);
		newFlight.setPilot(flight.getPilot());
		newFlight.setAirplane(flight.getAirplane());
		newFlight.setArrive(flight.getArrive());
		newFlight.setDeparture(flight.getDeparture());
		newFlight.setCitySource(flight.getCitySource());
		newFlight.setCityDestiny(flight.getCityDestiny());
		newFlight.setStatus(StatusFlight.INFLIGHT);	
		
		flghtService.update(15l, newFlight);	
		
	}
	
	@Test
	public void update_flight_sucess() {
		
		Flight flight = getNewFlight();		
		when(flightMock.findById(15L)).thenReturn(Optional.of(flight));
		
		Flight newFlight = new Flight();
		newFlight.setId(15l);
		newFlight.setPilot(flight.getPilot());
		newFlight.setAirplane(flight.getAirplane());
		newFlight.setArrive(flight.getArrive());
		newFlight.setDeparture(flight.getDeparture());
		newFlight.setCitySource(flight.getCitySource());
		newFlight.setCityDestiny(flight.getCityDestiny());
		newFlight.setStatus(StatusFlight.INFLIGHT);
		
		when(flightMock.save(newFlight)).thenReturn(newFlight);
		
		Flight save = flghtService.update(15l, newFlight);
		
		assertThat(save.getStatus(), equalTo(StatusFlight.INFLIGHT));
		
	}

	@Test(expected = PilotInvalidException.class)
	public void fail_insert_new_flight() {

		Flight flight = getNewFlight();

		when(flightMock.verifyPilotBusy(flight.getDeparture(), flight.getArrive(), flight.getPilot().getId(),
				flight.getStatus())).thenReturn(Optional.of(flight));

		Flight newFlight = new Flight();
		newFlight.setId(15l);
		newFlight.setPilot(flight.getPilot());
		newFlight.setAirplane(flight.getAirplane());
		newFlight.setArrive(flight.getArrive());
		newFlight.setDeparture(flight.getDeparture());
		newFlight.setCitySource(flight.getCitySource());
		newFlight.setCityDestiny(flight.getCityDestiny());
		newFlight.setStatus(StatusFlight.READY);

		flghtService.save(newFlight);

	}

	@Test
	public void create_new_flight() {
		Flight flight = getNewFlight();

		when(flightMock.verifyPilotBusy(flight.getDeparture(), flight.getArrive(), flight.getPilot().getId(),
				flight.getStatus())).thenReturn(Optional.empty());

		Flight newFlight = new Flight();
		newFlight.setId(15l);
		newFlight.setPilot(flight.getPilot());
		newFlight.setAirplane(flight.getAirplane());
		newFlight.setArrive(flight.getArrive());
		newFlight.setDeparture(flight.getDeparture());
		newFlight.setCitySource(flight.getCitySource());
		newFlight.setCityDestiny(flight.getCityDestiny());
		newFlight.setStatus(StatusFlight.READY);

		when(flightMock.save(newFlight)).thenReturn(newFlight);
		
		Flight save = flghtService.save(newFlight);
		
		assertThat(save.getId(), equalTo(15l));
	}
	
	@Test(expected = DepartureInvalidException.class)
	public void fail_departure_invalid_exception_create_flight() {
		Flight flight = getNewFlight();

		when(flightMock.verifyPilotBusy(flight.getDeparture(), flight.getArrive(), flight.getPilot().getId(),
				flight.getStatus())).thenReturn(Optional.empty());		
		
		Flight newFlight = new Flight();
		newFlight.setId(15l);
		newFlight.setPilot(flight.getPilot());
		newFlight.setAirplane(flight.getAirplane());
		newFlight.setArrive(flight.getDeparture());
		newFlight.setDeparture(flight.getArrive());
		newFlight.setCitySource(flight.getCitySource());
		newFlight.setCityDestiny(flight.getCityDestiny());
		newFlight.setStatus(StatusFlight.READY);	
		
		flghtService.save(newFlight);
	}
	
	@Test(expected = CitySameInvalidException.class)
	public void fail_city_same_exception_create_flight() {
		Flight flight = getNewFlight();

		when(flightMock.verifyPilotBusy(flight.getDeparture(), flight.getArrive(), flight.getPilot().getId(),
				flight.getStatus())).thenReturn(Optional.empty());

		Flight newFlight = new Flight();
		newFlight.setId(15l);
		newFlight.setPilot(flight.getPilot());
		newFlight.setAirplane(flight.getAirplane());
		newFlight.setArrive(flight.getArrive());
		newFlight.setDeparture(flight.getDeparture());
		newFlight.setCitySource(flight.getCitySource());
		newFlight.setCityDestiny(flight.getCitySource());
		newFlight.setStatus(StatusFlight.READY);
		
		flghtService.save(newFlight);
	}
	
	@Test(expected = StatusFlightInvalidException.class)
	public void fail_status_flight_exception_create_flight() {
		
		Flight flight = getNewFlight();

		when(flightMock.verifyPilotBusy(flight.getDeparture(), flight.getArrive(), flight.getPilot().getId(),
				flight.getStatus())).thenReturn(Optional.empty());

		Flight newFlight = new Flight();
		newFlight.setId(15l);
		newFlight.setPilot(flight.getPilot());
		newFlight.setAirplane(flight.getAirplane());
		newFlight.setArrive(flight.getArrive());
		newFlight.setDeparture(flight.getDeparture());
		newFlight.setCitySource(flight.getCitySource());
		newFlight.setCityDestiny(flight.getCityDestiny());
		newFlight.setStatus(StatusFlight.INFLIGHT);
		
		flghtService.save(newFlight);
	}
}
