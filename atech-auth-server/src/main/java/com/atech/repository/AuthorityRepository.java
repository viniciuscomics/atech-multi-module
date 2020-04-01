package com.atech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atech.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long>{
	public Authority findByName(String name);
}
