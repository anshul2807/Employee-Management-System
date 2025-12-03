package com.anshul.EmployeeManagementSystem.Service;

import com.anshul.EmployeeManagementSystem.Entity.Department;
import com.anshul.EmployeeManagementSystem.Entity.JobTitle;
import com.anshul.EmployeeManagementSystem.Repository.DepartmentRepository;
import com.anshul.EmployeeManagementSystem.Repository.JobTitleRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LookupService {
    // get all department

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private JobTitleRepository jobTitleRepository;
    public List<Department> getAllDepartment(){
        return departmentRepository.findAll();
    }

    public void saveDepartment(Department dept){
        departmentRepository.save(dept);
    }
    public List<JobTitle> getAllDJobTitles(){
        return jobTitleRepository.findAll();
    }

    public void saveJobTitle(JobTitle jobTitle){
        jobTitleRepository.save(jobTitle);
    }
    // check if departmentExists(id) or not
    public boolean departmentExists(ObjectId myid){
        return departmentRepository.existsById(myid);
    }
}
