package com.anshul.EmployeeManagementSystem.Controller;

import com.anshul.EmployeeManagementSystem.Entity.Department;
import com.anshul.EmployeeManagementSystem.Entity.JobTitle;
import com.anshul.EmployeeManagementSystem.Service.LookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lookups")
public class LookupController {
    @Autowired
    private LookupService lookupService;

    @GetMapping("/departments")
    public ResponseEntity<?> getAllDepartments(){
        try {
            SecurityContextHolder.getContext().getAuthentication();
            List<Department> listDept = lookupService.getAllDepartment();
            if(listDept.isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(listDept,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/departments")
    public ResponseEntity<?> createDepartment(@RequestBody Department dept){
        try {
            lookupService.saveDepartment(dept);
            return new ResponseEntity<>(dept,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/job-titles")
    public ResponseEntity<?> getAllJobTitles(){
        try {
            List<JobTitle> listjobtitles = lookupService.getAllDJobTitles();
            if(listjobtitles.isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(listjobtitles,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/job-titles")
    public ResponseEntity<?> createJobTitle(@RequestBody JobTitle jobtitle){
        try {
            lookupService.saveJobTitle(jobtitle);
            return new ResponseEntity<>(jobtitle,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
