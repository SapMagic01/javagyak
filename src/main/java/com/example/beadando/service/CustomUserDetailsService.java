package com.example.beadando.service;

import com.example.beadando.entity.User;
import com.example.beadando.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Megkeressük a felhasználót az adatbázisban
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Nem található felhasználó: " + username));

        // Átalakítjuk a Spring saját UserDetails formátumára
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword()) // Ez már a titkosított jelszó
                .authorities(user.getRole())  // pl. "ROLE_ADMIN"
                .build();
    }
}