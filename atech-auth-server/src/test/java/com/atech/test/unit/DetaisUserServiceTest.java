package com.atech.test.unit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import com.atech.model.Authority;
import com.atech.model.User;
import com.atech.repository.UserRepository;
import com.atech.service.DetailsUserService;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DetaisUserServiceTest {

	private DetailsUserService detaisUserService;
	
	@Mock
	private UserRepository userMockRepository;
	
	@Before
	public void setUp() {		
		MockitoAnnotations.initMocks(this);		
		detaisUserService = new DetailsUserService(userMockRepository);
	}
	
	@Test
	public void load_by_user_name() {		
			
		User inDataBase = new User();
		inDataBase.setId(10l);
		inDataBase.setActive(true);
		inDataBase.setEmail("vinicius_csa@yahoo.com.br");
		inDataBase.setPassword("12345678");
		inDataBase.setUsername("vinicius");
		
		Set<Authority> authorities = new HashSet<>();
		Authority at = new Authority();
		at.setId(1l);
		at.setName("ROLE_USER");
		authorities.add(at);
		
		inDataBase.setAuthorities(authorities);
		
		when(userMockRepository.findByUsername("vinicius")).thenReturn(Optional.of(inDataBase));		
		
		UserDetails findUser = detaisUserService.loadUserByUsername("vinicius");	
		
		assertThat(findUser.getUsername(), equalTo("vinicius"));
		
	}
	
	@Test(expected = UsernameNotFoundException.class)
	public void load_by_user_name_fail() {		
			
		User inDataBase = new User();
		inDataBase.setId(10l);
		inDataBase.setActive(true);
		inDataBase.setEmail("vinicius_csa@yahoo.com.br");
		inDataBase.setPassword("12345678");
		inDataBase.setUsername("vinicius");		
		
		when(userMockRepository.findByUsername("vinicius")).thenReturn(Optional.empty());		
		
		detaisUserService.loadUserByUsername("vinicius");	
		
	}
	
	@Test
	public void load_by_user_name_valid_authorities() {		
			
		User inDataBase = new User();
		inDataBase.setId(10l);
		inDataBase.setActive(true);
		inDataBase.setEmail("vinicius_csa@yahoo.com.br");
		inDataBase.setPassword("12345678");
		inDataBase.setUsername("vinicius");
		
		Set<Authority> authorities = new HashSet<>();
		Authority at = new Authority();
		at.setId(1l);
		at.setName("ROLE_USER");
		authorities.add(at);
		
		inDataBase.setAuthorities(authorities);
		
		when(userMockRepository.findByUsername("vinicius")).thenReturn(Optional.of(inDataBase));		
		
		UserDetails findUser = detaisUserService.loadUserByUsername("vinicius");	
		
		findUser.getAuthorities().forEach(e->{			
			assertThat(e.getAuthority(), equalTo("ROLE_USER"));			
		});		
	}	
	
}
