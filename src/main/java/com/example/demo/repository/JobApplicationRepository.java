package com.example.demo.repository;

import com.example.demo.model.JobApplication;
import com.example.demo.model.Status;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {

    List<JobApplication> findByStatus(Status status);
    List<JobApplication> findByUser(User user);
}
