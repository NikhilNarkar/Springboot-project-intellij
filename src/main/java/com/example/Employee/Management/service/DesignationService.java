package com.example.Employee.Management.service;

import com.example.Employee.Management.Designation;
import com.example.Employee.Management.repo.DesignationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DesignationService {
    @Autowired
    public DesignationRepository designationRepository;

    public List<Designation> getAllDesignations() {
        return designationRepository.findAll();  // avoid null
    }

    public Optional<Designation> getDesignationById(Long id){
        return designationRepository.findById(id);
    }
    public void saveDesignation(Designation designation){
        designationRepository.save(designation);
    }
    public void deleteDesignation(Long id){
        designationRepository.deleteById(id);
    }

}
