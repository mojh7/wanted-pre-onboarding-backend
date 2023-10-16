package com.wanted.preonboarding.jobpost.controller;

import com.wanted.preonboarding.common.ApiResponse;
import com.wanted.preonboarding.jobpost.dto.request.JobPostCreateRequest;
import com.wanted.preonboarding.jobpost.dto.request.JobPostUpdateRequest;
import com.wanted.preonboarding.jobpost.service.JobPostService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class JobPostController {
    private final JobPostService jobPostService;

    public JobPostController(final JobPostService jobPostService) {
        this.jobPostService = jobPostService;
    }

    @PostMapping("/job-post")
    public ApiResponse<?> createJobPost(@RequestBody @Valid JobPostCreateRequest jobPostCreateRequest) {
        jobPostService.createJobPost(jobPostCreateRequest);
        return ApiResponse.succeed();
    }

    @PutMapping("/job-post/{jobPostId}")
    public ApiResponse<?> updateJobPost(@RequestBody @Valid JobPostUpdateRequest jobPostUpdateRequest,
                                        @PathVariable Long jobPostId) {
        jobPostService.updateJobPost(jobPostUpdateRequest, jobPostId);
        return ApiResponse.succeed();
    }

    @DeleteMapping("/job-post/{jobPostId}")
    public ApiResponse<?> deleteJobPost(@PathVariable Long jobPostId) {
        jobPostService.deleteJobPost(jobPostId);
        return ApiResponse.succeed();
    }
}
