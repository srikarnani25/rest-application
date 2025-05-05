package com.udemy.rest.service;
import com.udemy.rest.model.User;
import com.udemy.rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(null);
    }
    public User createUser(User user){
        return userRepository.save(user);
    }
    public User updateUser(Long id, User userDetails){
        User user = userRepository.findById(id).orElseThrow();
        user.setName(userDetails.getName());
        user.setAge(userDetails.getAge());
        return userRepository.save(user);
    }
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
