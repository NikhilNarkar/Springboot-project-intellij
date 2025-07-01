package com.example.Employee.Management.service;

import com.example.Employee.Management.Employee;
import com.example.Employee.Management.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {


//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();

    }

    public Optional<Employee>getEmployeeById(Long id){
        return employeeRepository.findById(id);

    }
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveEmployee(Employee employee){
//        String rawPassword = employee.getPassword();
//        String encodedPassword = passwordEncoder.encode(rawPassword);
//        employee.setPassword(encodedPassword);
        if (employee.getPassword() != null && !employee.getPassword().isEmpty()) {
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        } // BCrypt encoding
        employeeRepository.save(employee);
    }
    public void deleteEmployee(Long id){
        employeeRepository.deleteById(id);
    }


//    public boolean authenticate(String email, String rawPassword) {
//        Optional<Employee> empOpt = employeeRepository.findByEmail(email);
//        if (empOpt.isPresent()) {
//            String encodedPassword = empOpt.get().getPassword();
//            return passwordEncoder.matches(rawPassword, encodedPassword);
//        }
//        return false;
//    }


}
