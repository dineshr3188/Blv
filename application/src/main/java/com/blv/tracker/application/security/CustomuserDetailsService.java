package com.blv.tracker.application.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blv.tracker.application.entity.Role;
import com.blv.tracker.application.entity.User;
import com.blv.tracker.application.exception.ResourceNotFoundException;
import com.blv.tracker.application.repository.UserRepository;



@Service
public class CustomuserDetailsService implements UserDetailsService {

	private UserRepository userRepository;
	
	public CustomuserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		User user=userRepository.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail).orElseThrow(()-> new ResourceNotFoundException("User not found with ",usernameOrEmail,(long) 1));
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),mapRolesToAuthorities(user.getRoles()));
	}
	private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
	

}
