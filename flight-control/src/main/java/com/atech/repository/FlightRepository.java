package com.atech.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.atech.model.Flight;
import com.atech.model.StatusFlight;

public interface FlightRepository extends JpaRepository<Flight,Long>{
	
	@Query("SELECT f FROM Flight f WHERE f.departure BETWEEN :departure AND :arrive AND f.pilot.id = :id AND f.status = :status")
	public Optional<Flight> verifyPilotBusy(LocalDateTime departure, LocalDateTime arrive, Long id, StatusFlight status);
}