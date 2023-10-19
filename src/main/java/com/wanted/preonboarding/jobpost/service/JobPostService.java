package com.wanted.preonboarding.jobpost.service;

import com.wanted.preonboarding.common.exception.ApplicationException;
import com.wanted.preonboarding.common.exception.ErrorCode;
import com.wanted.preonboarding.company.entity.Company;
import com.wanted.preonboarding.company.repository.CompanyRepository;
import com.wanted.preonboarding.jobpost.dto.request.JobPostCreateRequest;
import com.wanted.preonboarding.jobpost.dto.request.JobPostUpdateRequest;
import com.wanted.preonboarding.jobpost.dto.response.JobPostDetailResponse;
import com.wanted.preonboarding.jobpost.dto.response.JobPostResponse;
import com.wanted.preonboarding.jobpost.entity.JobPost;
import com.wanted.preonboarding.jobpost.repository.JobPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobPostService {
    private final JobPostRepository jobPostRepository;
    private final CompanyRepository companyRepository;

    public JobPostService(final JobPostRepository jobPostRepository, final CompanyRepository companyRepository) {
        this.jobPostRepository = jobPostRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public void createJobPost(JobPostCreateRequest request) {
        Company company = companyRepository.findById(request.getCompanyId())
                                           .orElseThrow(() -> new ApplicationException(ErrorCode.COMPANY_NOT_FOUND));

        JobPost jobPost = request.toEntity(company);
        jobPostRepository.save(jobPost);
    }

    public List<JobPostResponse> retrieveJobPostList() {
        return jobPostRepository.findAllByIsDeletedFalse().stream()
                                .map(JobPostResponse::from)
                                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public JobPostDetailResponse retrieveJobPostDetail(Long jobPostId) {
        JobPost jobPost = jobPostRepository.findByIdAndIsDeletedFalse(jobPostId)
                                           .orElseThrow(() -> new ApplicationException(ErrorCode.JOBPOST_NOT_FOUND));

        List<Long> companyOtherJobPostList = jobPost.getCompany().getJobPostList().stream()
                                                    .filter(jp -> !jp.isDeleted())
                                                    .map(JobPost::getId)
                                                    .collect(Collectors.toList());

        return JobPostDetailResponse.of(jobPost, companyOtherJobPostList);
    }

    public List<JobPostResponse> searchJobPost(String keyword) {
        return jobPostRepository.fullTextSearch(keyword).stream()
                                .map(JobPostResponse::from)
                                .collect(Collectors.toList());
    }

    @Transactional
    public void updateJobPost(JobPostUpdateRequest request, long jobPostId) {
        JobPost jobPost = jobPostRepository.findByIdAndIsDeletedFalse(jobPostId)
                                           .orElseThrow(() -> new ApplicationException(ErrorCode.JOBPOST_NOT_FOUND));

        if(jobPost.getCompany().getId() != request.getCompanyId()) {
            throw new ApplicationException(ErrorCode.UNABLE_TO_UPDATE_FIELDS, " : company_id");
        }

        JobPost newJobPost = request.toEntity(jobPost.getCompany());
        jobPost.update(newJobPost);
        jobPostRepository.save(jobPost);
    }

    @Transactional
    public void deleteJobPost(long jobPostId) {
        JobPost jobPost = jobPostRepository.findByIdAndIsDeletedFalse(jobPostId)
                                           .orElseThrow(() -> new ApplicationException(ErrorCode.JOBPOST_NOT_FOUND));
        jobPostRepository.delete(jobPost);
    }
}
