package com.wanted.preonboarding.jobpost.repository;

import com.wanted.preonboarding.jobpost.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JobPostRepository extends JpaRepository<JobPost, Long>{

    Optional<JobPost> findByIdAndIsDeletedFalse(Long id);

    List<JobPost> findAllByIsDeletedFalse();

    @Query(value = "SELECT * FROM job_post as j " +
            "INNER JOIN company as c ON j.company_id = c.id AND j.is_deleted = false " +
            "WHERE MATCH(j.position, j.skills, j.description) AGAINST ('+':keyword'*' in boolean mode) " +
            "OR MATCH(c.name, c.nation, c.region) AGAINST ('+':keyword'*' in boolean mode);", nativeQuery = true)
    List<JobPost> fullTextSearch(@Param("keyword") String keyword);
}