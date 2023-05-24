package com.spring.identity.config;

import com.spring.identity.entity.CustomUserDetails;
import com.spring.identity.entity.UserCredential;
import com.spring.identity.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserCredentialRepository userCredentialRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<UserCredential> credential=userCredentialRepository.findByName(username);
        return credential.map(CustomUserDetails::new).orElseThrow(()-> new UsernameNotFoundException("user not with"+username));
    }
}
