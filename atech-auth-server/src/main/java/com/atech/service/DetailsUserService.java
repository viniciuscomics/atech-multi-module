package com.atech.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.atech.model.User;
import com.atech.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class DetailsUserService implements UserDetailsService{	
	
	
	private UserRepository userRepository;
	
	private UserDetails userDetails;
	
	public DetailsUserService(@Autowired UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.info("Find user -> "+ username);
		userDetails = userRepository.findByUsername(username)
								.map(user -> new org.springframework.security.core.userdetails
								.User(user.getUsername(), user.getPassword(), user.isActive()
								,true,true,true, getGrantedAuthorities(user)))
								.orElseThrow(() -> new UsernameNotFoundException("User "+username+" Not found"));			
		
		log.info("User Find "+userDetails.getUsername());
		
		return userDetails;
	}

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	private Collection<GrantedAuthority> getGrantedAuthorities(User user){
    	Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    	
    	if(user.getAuthorities() != null) {
    		
    		user.getAuthorities().forEach(e->{
    			grantedAuthorities.add(new SimpleGrantedAuthority(e.getName()));
    		});        
    	}
        
        return grantedAuthorities;
    }
	
}
