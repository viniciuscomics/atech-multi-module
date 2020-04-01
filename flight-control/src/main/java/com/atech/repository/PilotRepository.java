package com.atech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atech.model.Pilot;

public interface PilotRepository extends JpaRepository<Pilot, Long>{

}
