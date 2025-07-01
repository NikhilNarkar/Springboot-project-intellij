package com.example.Employee.Management.service;

import com.example.Employee.Management.Employee;
import com.example.Employee.Management.repo.EmployeeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomEmployeeDetailsService implements UserDetailsService {

    @Autowired
    private HttpSession session;

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public UserDetails loadUserByUsername(String idStr) throws UsernameNotFoundException {
        Long id = Long.parseLong(idStr);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found with ID: " + id));




        session.setAttribute("employeeId", employee.getId());
        session.setAttribute("employeeName", employee.getName());

        return User.builder()
                .username(String.valueOf(employee.getId()))
                .password(employee.getPassword())
                .roles("USER")
                .build();
    }
}
