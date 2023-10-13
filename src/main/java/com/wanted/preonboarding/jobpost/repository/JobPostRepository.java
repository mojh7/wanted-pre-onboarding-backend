package com.wanted.preonboarding.jobpost.repository;

import com.wanted.preonboarding.jobpost.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostRepository extends JpaRepository<JobPost, Long>{

}