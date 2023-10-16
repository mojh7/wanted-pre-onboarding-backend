package com.wanted.preonboarding.jobpost.service;

import com.wanted.preonboarding.common.exception.ApplicationException;
import com.wanted.preonboarding.common.exception.ErrorCode;
import com.wanted.preonboarding.company.entity.Company;
import com.wanted.preonboarding.company.repository.CompanyRepository;
import com.wanted.preonboarding.jobpost.dto.request.JobPostCreateRequest;
import com.wanted.preonboarding.jobpost.dto.request.JobPostUpdateRequest;
import com.wanted.preonboarding.jobpost.entity.JobPost;
import com.wanted.preonboarding.jobpost.repository.JobPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
