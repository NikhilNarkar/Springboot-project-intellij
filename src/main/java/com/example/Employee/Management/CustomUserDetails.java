//package com.example.Employee.Management;
//
//import com.example.Employee.Management.Employee;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Collections;
//
//public class CustomUserDetails implements UserDetails {
//
//    private final Employee employee;
//
//    public CustomUserDetails(Employee employee) {
//        this.employee = employee;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.emptyList(); // or set roles later
//    }
//
//    @Override
//    public String getPassword() {
//        return employee.getPassword(); // must be encoded
//    }
//
//    @Override
//    public String getUsername() {
//        return employee.getEmail(); // assuming email is used as login
//    }
//
//    @Override public boolean isAccountNonExpired() { return true; }
//    @Override public boolean isAccountNonLocked() { return true; }
//    @Override public boolean isCredentialsNonExpired() { return true; }
//    @Override public boolean isEnabled() { return true; }
//}
