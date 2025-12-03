package com.anshul.EmployeeManagementSystem.Service;


import com.anshul.EmployeeManagementSystem.Entity.Employee;
import com.anshul.EmployeeManagementSystem.Entity.User;
import com.anshul.EmployeeManagementSystem.Repository.DepartmentRepository;
import com.anshul.EmployeeManagementSystem.Repository.EmployeeRepository;
import com.anshul.EmployeeManagementSystem.Repository.JobTitleRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private JobTitleRepository jobTitleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Transactional
    public Employee createEmployee(Employee emp,String role){
        if(employeeRepository.findByEmail(emp.getEmail()) != null)
            return null;
        if(!departmentRepository.existsById(emp.getDepartmentId()))
            return null;
        if(!jobTitleRepository.existsById(emp.getJobTitleId()))
            return null;

        emp.setRole(Arrays.asList(role));
        emp.setTotalLeaveBalanceDays(0.0);
        emp.setStatus("ACTIVE");
        Employee saveEmp = employeeRepository.save(emp);
        User newUser = new User();
        newUser.setUsername(saveEmp.getFirstName());
        newUser.setPassword(passwordEncoder.encode("default"));
        newUser.setRole(Arrays.asList("USER"));
        newUser.setEmpRef(saveEmp);
        userService.saveEntry(newUser);
        return saveEmp;
    }

//    Employee updateEmployee(String id, EmployeeDTO employeeDTO);

//    Employee updateJobAndDept(String id, String newJobTitleId, String newDepartmentId);

    public List<Employee> getDirectReports(ObjectId managerId){
        return employeeRepository.findByManagerId(managerId);
    }

    public void terminateEmployee(ObjectId id){
        employeeRepository.deleteById(id);
    }


    public Employee getEmployeeById(ObjectId id){
      Optional<Employee> emp =  employeeRepository.findById(id);
      return emp.orElse(null);
    }
    public List<Employee> getAll(){
        return employeeRepository.findAll();
    }
}
