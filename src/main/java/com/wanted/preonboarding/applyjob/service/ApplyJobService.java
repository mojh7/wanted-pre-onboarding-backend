package com.wanted.preonboarding.applyjob.service;

import com.wanted.preonboarding.applyjob.dto.request.ApplyJobCreateRequest;
import com.wanted.preonboarding.applyjob.entity.ApplyJob;
import com.wanted.preonboarding.applyjob.repository.ApplyJobRepository;
import com.wanted.preonboarding.common.exception.ApplicationException;
import com.wanted.preonboarding.common.exception.ErrorCode;
import com.wanted.preonboarding.jobpost.entity.JobPost;
import com.wanted.preonboarding.jobpost.repository.JobPostRepository;
import com.wanted.preonboarding.member.entity.Member;
import com.wanted.preonboarding.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplyJobService {
    private final ApplyJobRepository applyJobRepository;
    private final JobPostRepository jobPostRepository;
    private final MemberRepository memberRepository;

    public ApplyJobService(final ApplyJobRepository applyJobRepository, final JobPostRepository jobPostRepository,
                           final MemberRepository memberRepository) {
        this.applyJobRepository = applyJobRepository;
        this.jobPostRepository = jobPostRepository;
        this.memberRepository = memberRepository;
    }


    @Transactional
    public void applyJobPost(ApplyJobCreateRequest request) {
        JobPost jobPost = jobPostRepository.findByIdAndIsDeletedFalse(request.getJobPostId())
                                           .orElseThrow(() -> new ApplicationException(ErrorCode.JOBPOST_NOT_FOUND));
        Member member = memberRepository.findById(request.getMemberId())
                                        .orElseThrow(() -> new ApplicationException(ErrorCode.MEMBER_NOT_FOUND));

        if(applyJobRepository.findByJobPostAndMember(jobPost, member).isPresent()) {
            throw new ApplicationException(ErrorCode.ALREADY_APPLY_JOBPOST);
        }

        ApplyJob applyJob = ApplyJob.builder()
                                    .jobPost(jobPost)
                                    .member(member)
                                    .build();

        applyJobRepository.save(applyJob);
    }
}
