package com.anshul.EmployeeManagementSystem.Controller;

import com.anshul.EmployeeManagementSystem.Entity.Employee;
import com.anshul.EmployeeManagementSystem.Entity.User;
import com.anshul.EmployeeManagementSystem.Repository.UserRepository;
import com.anshul.EmployeeManagementSystem.Service.EmployeeService;
import com.anshul.EmployeeManagementSystem.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody Employee emp){
        try {
            Employee saveEmp = employeeService.createEmployee(emp, "EMPLOYEE");
            if(saveEmp==null)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(emp, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllEmployees(){
        try {
            List<Employee> all_emp = employeeService.getAll();
            if(all_emp == null || all_emp.isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(all_emp,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/{myid}")
    public ResponseEntity<?> getEmployeeById(@PathVariable ObjectId myid){
        try {
            Employee employeeById = employeeService.getEmployeeById(myid);
            if(employeeById == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(employeeById,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    UPDATE ALL
//    PARTIAL UPDATE
    @DeleteMapping("/{myid}/terminate")
    public ResponseEntity<?> deleteEmployee(@PathVariable ObjectId myid){
        try {
            User userById = userService.findUserById(myid);
            if(userById == null)
                return new ResponseEntity<>("User Not Found",HttpStatus.NOT_FOUND);
            employeeService.terminateEmployee(userById.getEmpRef().getId());
            userService.terminateUser(userById);
            return new ResponseEntity<>("Employee Terminated",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("{managerId}/reports")
    public ResponseEntity<?> getEmployeesReportingToManager(@PathVariable ObjectId managerId){
        try {
            List<Employee> lists = employeeService.getDirectReports(managerId);
            if(lists ==null || lists.isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(lists,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
