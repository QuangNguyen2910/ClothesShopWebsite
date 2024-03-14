package com.example.project_economic.impl;

import com.example.project_economic.entity.UserEntity;
import com.example.project_economic.repository.UserRepository;
import com.example.project_economic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserEntity createUser(UserEntity userEntity) throws Exception {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles("USER");
        Optional<UserEntity>user=this.userRepository.findByUsername(userEntity.getUsername());
        if(user.isPresent()){
            throw new Exception("Tài khoản "+ userEntity.getUsername()+" đã tồn tại!");
        }
        UserEntity userSaved=this.userRepository.save(userEntity);
        return userSaved;
    }
    @Override
    public UserEntity findUserById(Long userId) {
        return this.userRepository.findById(userId).get();
    }
    @Override
    public UserEntity update(UserEntity userEntity, Long userId){
        UserEntity userEntityFind = new UserEntity();
        try{
            userEntityFind = this.userRepository.findById(userId).get();
            userEntityFind.setPassword(this.userRepository.findById(userId).get().getPassword());
            userEntityFind.setUsername(userEntity.getUsername() != "" ? userEntity.getUsername() : userEntityFind.getUsername());
            userEntityFind.setEmail(userEntity.getEmail() != "" ? userEntity.getEmail() : userEntityFind.getEmail());
            userEntityFind.setAddress(userEntity.getAddress() != "" ?  userEntity.getAddress() : userEntityFind.getAddress());
            userEntityFind.setPhoneNumber(userEntity.getPhoneNumber() != "" ?  userEntity.getPhoneNumber() : userEntityFind.getPhoneNumber());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return this.userRepository.save(userEntityFind);
    }
}
