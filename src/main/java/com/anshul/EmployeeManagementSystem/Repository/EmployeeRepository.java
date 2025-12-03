package com.anshul.EmployeeManagementSystem.Repository;

import com.anshul.EmployeeManagementSystem.Entity.Employee;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmployeeRepository extends MongoRepository<Employee, ObjectId> {
    Employee findByEmail(String email);
    List<Employee> findByManagerId(ObjectId managerId);
}
