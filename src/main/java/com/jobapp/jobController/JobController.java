package com.jobapp.jobController;

import com.jobapp.domain.Job;
import com.jobapp.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
public class JobController {

    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> findAll(){
        return new ResponseEntity<>(jobService.findAll(),HttpStatus.OK);
    }

    @PostMapping("/jobs")
    public ResponseEntity<String> createJob(@RequestBody Job job){
        jobService.createJob(job);
        return new ResponseEntity<>("job added",HttpStatus.CREATED);
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<Job> getJobByID(@PathVariable Long id){
        Job job = jobService.getJobById(id);
            if(job == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(job,HttpStatus.OK);
    }

    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id){
        boolean jobDeleted = jobService.deleteJobById(id);
        if(jobDeleted){
            return new ResponseEntity<>("job deleted", HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/jobs/{id}")
    public ResponseEntity<String>updateJob(@PathVariable Long id,
                                           @RequestBody Job updatedJob){
        boolean updated  = jobService.updateJob(id,updatedJob);

        if(updated)
            return  new ResponseEntity<>("job  updated",HttpStatus.OK);
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
