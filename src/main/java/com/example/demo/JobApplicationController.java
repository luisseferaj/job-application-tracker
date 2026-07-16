package com.example.demo;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobApplication")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    @GetMapping
    public List<JobApplicationDTO> getAll(@RequestParam(required = false) Status status){

        return jobApplicationService.getAll(status);
    }

    @PostMapping
    public JobApplicationDTO add(@Valid @RequestBody JobApplicationDTO dto){
        return jobApplicationService.add(dto);
    }

    @PutMapping("/{id}")
    public JobApplication update(@PathVariable int id, @RequestBody JobApplication jobApplication){
        return jobApplicationService.update(id, jobApplication);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        jobApplicationService.delete(id);
    }
}
