package com.anshul.EmployeeManagementSystem.Repository;

import com.anshul.EmployeeManagementSystem.Entity.Department;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentRepository extends MongoRepository<Department, ObjectId> {
}
