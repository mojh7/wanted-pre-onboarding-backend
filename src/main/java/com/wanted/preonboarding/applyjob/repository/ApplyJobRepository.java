package com.wanted.preonboarding.applyjob.repository;

import com.wanted.preonboarding.applyjob.entity.ApplyJob;
import com.wanted.preonboarding.jobpost.entity.JobPost;
import com.wanted.preonboarding.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplyJobRepository extends JpaRepository<ApplyJob, Long> {
    Optional<ApplyJob> findByJobPostAndMember(JobPost jobPost, Member member);
}
