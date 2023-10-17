package com.wanted.preonboarding.jobpost.repository;

import com.wanted.preonboarding.jobpost.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobPostRepository extends JpaRepository<JobPost, Long>{

    Optional<JobPost> findByIdAndIsDeletedFalse(Long id);

    List<JobPost> findAllByIsDeletedFalse();

}