package com.anshul.EmployeeManagementSystem.Repository;

import com.anshul.EmployeeManagementSystem.Entity.JobTitle;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobTitleRepository extends MongoRepository<JobTitle, ObjectId> {
}
