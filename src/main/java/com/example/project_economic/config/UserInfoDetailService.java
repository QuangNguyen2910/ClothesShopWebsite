package com.example.project_economic.config;

import com.example.project_economic.entity.UserEntity;
import com.example.project_economic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity>userEntity=this.userRepository.findByUsername(username);
        if(userEntity.isPresent()){
            return new UserInfoDetails(userEntity.get());
        }
        throw new UsernameNotFoundException("Username "+username+" not found");
    }
}
