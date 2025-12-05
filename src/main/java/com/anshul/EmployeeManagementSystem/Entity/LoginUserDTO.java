package com.anshul.EmployeeManagementSystem.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserDTO {
    private String token;
    private User user;
    private List<String> roles;
}
