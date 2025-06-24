package com.example.Employee.Management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DesignationService designationService;

    @GetMapping
    public String getAllEmployees(Model model){
        List<Employee>employees = employeeService.getAllEmployees();
        model.addAttribute("employees",employees);
        return "employee-list";

    }

    @GetMapping("/add")
    public String showAddEmployeeForm(Model model){
        System.out.println("&&&&&&&&&&");
        model.addAttribute("employee",new Employee());
        model.addAttribute("designations",designationService.getAllDesignations());
        return "employee-form";
    }
    @GetMapping("/edit/{id}")
    public String showEditEmployeeForm(@PathVariable Long id, Model model){
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if(employee.isPresent()){
            model.addAttribute("employee", employee.get());
            model.addAttribute("designations", designationService.getAllDesignations());
            return "employee-form";
        }else{
            return "redirect:/employees";
        }
    }
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee){
        employeeService.saveEmployee(employee);
        return  "redirect:/";
    }
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return "redirect:/";
    }


}
