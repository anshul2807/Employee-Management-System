package com.anshul.EmployeeManagementSystem.Service;

import com.anshul.EmployeeManagementSystem.Entity.Employee;
import com.anshul.EmployeeManagementSystem.Entity.User;
import com.anshul.EmployeeManagementSystem.Repository.EmployeeRepository;
import com.anshul.EmployeeManagementSystem.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public User saveEntry(User user){
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Error ->",e);
            return null;
        }
    }

    public boolean userExists(String username){
        User user = userRepository.findByUsername(username);
        return user!=null;
    }
    public void userUpdate(User user){
        userRepository.save(user);
    }
    public User findUserById(ObjectId userId){
        return userRepository.findById(userId).orElse(null);
    }
    public void terminateUser(User user){
        userRepository.deleteById(user.getId());
    }
}
