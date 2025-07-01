package com.example.Employee.Management.controller;

import com.example.Employee.Management.Employee;
import com.example.Employee.Management.service.DesignationService;
import com.example.Employee.Management.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DesignationService designationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Home - List all employees
    @GetMapping
    public String getAllEmployees(HttpSession session,Model model) {
        if (session.getAttribute("employeeId") == null) {
            return "redirect:/login";
        }
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employee-list";
    }

    // Show add employee form
    @GetMapping("/add")
    public String showAddEmployeeForm(HttpSession session,Model model) {
        if (session.getAttribute("employeeId") == null) {
            return "redirect:/login";
        }
        model.addAttribute("employee", new Employee());
        model.addAttribute("designations", designationService.getAllDesignations());
        return "employee-form";
    }

    // Show password verification form before edit
    @GetMapping("/edit/{id}")
    public String askPasswordBeforeEdit(@PathVariable Long id, HttpSession session, Model model) {
        if (session.getAttribute("employeeId") == null) {
            return "redirect:/login";
        }
        model.addAttribute("id", id);
        return "verify-password"; // A new page to verify password
    }

    // Verify password and proceed to edit
    @PostMapping("/verify-password")
    public String verifyPasswordForEdit(@RequestParam Long id,
                                        @RequestParam String password, HttpSession session,
                                        Model model,
                                        RedirectAttributes redirectAttributes) {
        if (session.getAttribute("employeeId") == null) {
            return "redirect:/login";
        }
        Optional<Employee> optionalEmployee = employeeService.getEmployeeById(id);
        if (optionalEmployee.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Employee not found!");
            return "redirect:/";
        }

        Employee employee = optionalEmployee.get();

        if (!passwordEncoder.matches(password, employee.getPassword())) {
            model.addAttribute("id", id);
            model.addAttribute("error", "Invalid password");
            return "verify-password";
        }

        // Password is correct, redirect to edit form
        return "redirect:/edit-secure/" + id;
    }

    // Actual edit form after password check
    @GetMapping("/edit-secure/{id}")
    public String showEditEmployeeForm(@PathVariable Long id, HttpSession session, Model model) {
        if (session.getAttribute("employeeId") == null) {
            return "redirect:/login";
        }
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
            model.addAttribute("designations", designationService.getAllDesignations());
            return "employee-form";
        }
        return "redirect:/";
    }

    // Save or update employee
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee,
                               BindingResult result,
                               RedirectAttributes redirectAttributes, HttpSession session,
                               Model model) {
        if (session.getAttribute("employeeId") == null) {
            return "redirect:/login";
        }
        if (result.hasErrors()) {
            model.addAttribute("designations", designationService.getAllDesignations());
            return "employee-form";
        }

        employeeService.saveEmployee(employee);
        redirectAttributes.addFlashAttribute("message", "You have saved your data successfully!");
        return "redirect:/";
    }

    // Delete employee
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("employeeId") == null) {
            return "redirect:/login";
        }
        employeeService.deleteEmployee(id);
        redirectAttributes.addFlashAttribute("message", "Employee deleted successfully!");
        return "redirect:/";
    }

    // Login page
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}
