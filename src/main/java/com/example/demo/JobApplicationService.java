package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private UserRepository userRepository;

    public List<JobApplicationDTO> getAll(Status status){
    return jobApplicationRepository.findByUser(getCurrentUser())
            .stream()
            .filter(a -> status == null || a.getStatus() == status)
            .map(this::toDto)
            .toList();
    }

    public JobApplicationDTO add(JobApplicationDTO dto){

        JobApplication jobApplication = new JobApplication();
        jobApplication.setCompany(dto.getCompany());
        jobApplication.setPosition(dto.getPosition());
        jobApplication.setStatus(dto.getStatus());
        jobApplication.setNotes(jobApplication.getNotes());
        jobApplication.setDateApplied(dto.getDateApplied());
        jobApplication.setUser(getCurrentUser());

        JobApplication saved =jobApplicationRepository.save(jobApplication);
    return toDto(saved);
    }

    public JobApplication update(int id, JobApplication updated){
        JobApplication existing = jobApplicationRepository.findById(id).orElseThrow();

        existing.setCompany(updated.getCompany());
        existing.setPosition(updated.getPosition());
        existing.setStatus(updated.getStatus());
        existing.setNotes(updated.getNotes());
        existing.setDateApplied(updated.getDateApplied());

        return jobApplicationRepository.save(existing);

    }

    public void delete(int id){
        jobApplicationRepository.deleteById(id);
    }

    public List<JobApplicationDTO> getByStatus(Status status){
        return jobApplicationRepository.findByStatus(status)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private JobApplicationDTO toDto(JobApplication jobApplication){
        JobApplicationDTO dto =new JobApplicationDTO();
        dto.setId(jobApplication.getId());
        dto.setCompany(jobApplication.getCompany());
        dto.setPosition(jobApplication.getPosition());
        dto.setStatus(jobApplication.getStatus());
        dto.setNotes(jobApplication.getNotes());
        dto.setDateApplied(jobApplication.getDateApplied());

        return dto;
    }

    private User getCurrentUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow();
    }


}
