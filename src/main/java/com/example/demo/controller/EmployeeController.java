package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @GetMapping("/employees/{document}")
    @Transactional(readOnly = true)
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "document") Long document)
            throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(document).orElseThrow(
                () -> new ResourceNotFoundException("Employee not found for this document :: " + document));
        return ResponseEntity.ok().body(employee);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployee() {
        return ResponseEntity.ok().body(employeeRepository.findAll());
    }

    @PutMapping("/employees/{document}")
    @Transactional
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "document") Long document,
                                                   @Valid @RequestBody Employee employeeDetails)
            throws ResourceNotFoundException {

        Employee employee = employeeRepository.findById(document)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Employee ot found for this document :: " + document));

        employee.setEmailId(employeeDetails.getEmailId());
        employee.setLastName(employeeDetails.getLastName());
        employee.setFirstName(employeeDetails.getFirstName());
        final Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/employees/{document}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "document") Long document)
        throws ResourceNotFoundException {

        Employee employee = employeeRepository.findById(document)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Employee ot found for this document :: " + document));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;

    }
}
