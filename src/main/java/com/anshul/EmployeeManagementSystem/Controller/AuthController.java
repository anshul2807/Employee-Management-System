package com.anshul.EmployeeManagementSystem.Controller;

import com.anshul.EmployeeManagementSystem.Entity.LoginUserDTO;
import com.anshul.EmployeeManagementSystem.Entity.User;
import com.anshul.EmployeeManagementSystem.Repository.UserRepository;
import com.anshul.EmployeeManagementSystem.Service.MyUserServiceDetails;
import com.anshul.EmployeeManagementSystem.Service.UserService;
import com.anshul.EmployeeManagementSystem.Utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserServiceDetails myUserServiceDetails;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        try {
            if(userService.userExists(user.getUsername()))
                return new ResponseEntity<>("ADMIN Already Exists", HttpStatus.BAD_REQUEST);
            user.setRole(Arrays.asList("ADMIN"));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User saveUser = userService.saveEntry(user);
            if(saveUser == null)
                return new ResponseEntity<>("Error in Request",HttpStatus.BAD_GATEWAY);
            return new ResponseEntity<>("ADMIN Created",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error in Request",HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
            UserDetails userDetails = myUserServiceDetails.loadUserByUsername(user.getUsername());
            User loginUser = userRepository.findByUsername(user.getUsername());
            List<String> allRoles = new ArrayList<>();
            if(loginUser.getEmpRef() != null){
                allRoles = loginUser.getEmpRef().getRole();
            }
            allRoles.addAll(loginUser.getRole());
            String authToken = jwtUtils.generateToken(loginUser.getId(),allRoles,userDetails.getUsername());
            LoginUserDTO loginUserDTO = LoginUserDTO.builder()
                    .token(authToken)
                    .roles(loginUser.getRole())
                    .user(loginUser).build();

            return new ResponseEntity<>(loginUserDTO,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error in Request",HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/pwd-change")
    public ResponseEntity<?> passwordChange(@RequestBody User updatedEntry){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userRepository.findByUsername(authentication.getName());
            user.setPassword(passwordEncoder.encode(updatedEntry.getPassword()));
            userService.userUpdate(user);
            return new ResponseEntity<>("Password Updated",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/admin-pwd-change")
    public ResponseEntity<?> passwordChangeByAdmin(@RequestBody User updatedEntry){
        try {
            User user = userRepository.findByUsername(updatedEntry.getUsername());
            if(user == null)
                return new ResponseEntity<>("User Not Found",HttpStatus.NOT_FOUND);
            user.setPassword(passwordEncoder.encode(updatedEntry.getPassword()));
            userService.userUpdate(user);
            return new ResponseEntity<>("Password Updated",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/all-users")
    public ResponseEntity<?> listAllUsers(){
        try {
            List<User> list = userService.findAllUsers();
            return new ResponseEntity<>(list,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
