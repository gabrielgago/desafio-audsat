package com.desafio.audsat.service;

import com.desafio.audsat.domain.UserAuthenticated;
import com.desafio.audsat.exception.AudsatException;
import com.desafio.audsat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).map(UserAuthenticated::new).orElseThrow(() -> new AudsatException("User not found"));
    }
}
