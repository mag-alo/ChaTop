package com.openclassrooms.chatop_backend.service;

import com.openclassrooms.chatop_backend.model.User;
import com.openclassrooms.chatop_backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {return userRepository.findAll();}
    public Optional<User> getUserById(Integer id) {return userRepository.findById(id);}
    public Optional<User> getUserByEmail(String email) {return userRepository.findByEmail(email);}
    public User saveUser(User user) {return userRepository.save(user);}
    public void deleteUser(Integer id) {userRepository.deleteById(id);}
    public UserRepository getUserRepository() {return userRepository;}

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
            .withUsername(user.getEmail())
            .password(user.getPassword())
            .authorities("USER")
            .build();
    }
}