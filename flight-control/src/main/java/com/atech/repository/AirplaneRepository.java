package com.atech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atech.model.Airplane;

public interface AirplaneRepository extends JpaRepository<Airplane, Long>{

}
