package com.example.Employee.Management.controller;

import com.example.Employee.Management.Designation;
import com.example.Employee.Management.service.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/designations")
public class DesignationController {

        @Autowired
        private DesignationService designationService;

        @GetMapping
        public String getAllDesignations(Model model) {
            List<Designation> designations = designationService.getAllDesignations();
            model.addAttribute("designations", designations);
            return "designation-list";
        }

        @GetMapping("/add")
        public String showAddDesignationForm(Model model) {
            model.addAttribute("designation", new Designation());
            return "designation-form";
        }

    @GetMapping("/edit/{id}")
    public String showEditDesignationForm(@PathVariable Long id, Model model) {
        Optional<Designation> designation = designationService.getDesignationById(id);
        if (designation.isPresent()) {
            model.addAttribute("designation", designation.get());
            return "designation-form";
        } else {
            return "redirect:/designations";
        }
    }

    @PostMapping("/save")
    public String saveDesignation(@ModelAttribute("designation") Designation designation) {
        designationService.saveDesignation(designation);
        return "redirect:/designations";
    }

    @GetMapping("/delete/{id}")
    public String deleteDesignation(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            designationService.deleteDesignation(id);
            redirectAttributes.addFlashAttribute("message", "Designation deleted successfully!");
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Designation not found!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting designation.");
        }
        return "redirect:/designations";
    }


}


