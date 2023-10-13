package com.wanted.preonboarding.jobpost.controller;

import com.wanted.preonboarding.common.ApiResponse;
import com.wanted.preonboarding.jobpost.dto.request.CreateJobPostRequest;
import com.wanted.preonboarding.jobpost.service.JobPostService;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ApiResponse<?> createJobPost(@RequestBody @Valid CreateJobPostRequest createJobPostRequest) {
        jobPostService.createJobPost(createJobPostRequest);
        return ApiResponse.succeed();
    }
}
