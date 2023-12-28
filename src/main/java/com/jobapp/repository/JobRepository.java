package com.jobapp.repository;

import com.jobapp.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository  extends JpaRepository<Job,Long> {
}
