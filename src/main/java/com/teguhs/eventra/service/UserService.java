package com.teguhs.eventra.service;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.teguhs.eventra.model.User;
import com.teguhs.eventra.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final Snowflake snowflake = IdUtil.getSnowflake(1, 1);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(long id){
        return userRepository.findById(id);
    }

    public User createUser(User user){
        if(userRepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("Email is already registered.");
        };

        user.setId(snowflake.nextId());
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser){
        Optional<User> existingUser = userRepository.findById(id);

        if(existingUser.isPresent()){
            // Update the fields
            User user = existingUser.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            user.setGender(updatedUser.getGender());
            user.setBirthDate(updatedUser.getBirthDate());
            user.setPhoneNumber(updatedUser.getPhoneNumber());

            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }
}