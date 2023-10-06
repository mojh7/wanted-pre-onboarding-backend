package com.wanted.preonboarding.recruitment.repository;

import com.wanted.preonboarding.recruitment.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostRepository extends JpaRepository<JobPost, Long>{

}