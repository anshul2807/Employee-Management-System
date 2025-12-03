package com.anshul.EmployeeManagementSystem.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
class Address{
    private String street;
    private String city;
    private String stateProvince;
    private String postalCode;
    private String country;
}

@Document(collection = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    private ObjectId id;

    private String firstName;
    private String lastName;
    private String email;

    private String phoneNumber;
    private LocalDate dateOfBirth;

    private Address address;

    private LocalDate joinDate;

    private ObjectId jobTitleId;
    private ObjectId departmentId;

    private ObjectId managerId;

    private String status;

    private List<String> role;

    private Double totalLeaveBalanceDays;

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
