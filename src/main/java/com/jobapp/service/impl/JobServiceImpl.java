package com.jobapp.service.impl;

import com.jobapp.domain.Job;
import com.jobapp.repository.JobRepository;
import com.jobapp.service.JobService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


@Service
public class JobServiceImpl implements JobService {
    JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }
    @Override
    public void createJob(Job job) {
            jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {
       return jobRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteJobById(Long id) {
      if(jobRepository.existsById(id)){
          jobRepository.deleteById(id);
          return true;
      }
      return false;
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job>jobOptional = jobRepository.findById(id);
            if(jobOptional.isPresent()){
                Job job = jobOptional.get();
                job.setTitle(updatedJob.getTitle());
                job.setDescription(updatedJob.getDescription());
                job.setLocation(updatedJob.getLocation());
                job.setMaxSalary(updatedJob.getMaxSalary());
                job.setMinSalary(updatedJob.getMinSalary());
                jobRepository.save(job);
                return true;
            }

        return false;
    }
}
