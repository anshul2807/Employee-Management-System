package com.anshul.EmployeeManagementSystem.Service;

import com.anshul.EmployeeManagementSystem.Entity.Employee;
import com.anshul.EmployeeManagementSystem.Entity.User;
import com.anshul.EmployeeManagementSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.stream.Stream;

@Controller
public class MyUserServiceDetails implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null)return null;
        Employee emp = user.getEmpRef();
        if(emp == null || emp.getRole() == null || emp.getRole().isEmpty()){
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole().toArray(new String[0]))
                    .build();
        }
        String[] empRoles = emp.getRole().toArray(new String[0]);
        String[] userRoles = user.getRole().toArray(new String[0]);

        String[] combinedRoleArray = Stream.concat(Arrays.stream(empRoles), Arrays.stream(userRoles))
                .toArray(String[]::new);

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(combinedRoleArray)
                .build();
    }
}
