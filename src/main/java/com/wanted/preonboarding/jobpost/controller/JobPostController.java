package com.wanted.preonboarding.jobpost.controller;

import com.wanted.preonboarding.common.ApiResponse;
import com.wanted.preonboarding.jobpost.dto.request.JobPostCreateRequest;
import com.wanted.preonboarding.jobpost.dto.request.JobPostUpdateRequest;
import com.wanted.preonboarding.jobpost.dto.response.JobPostDetailResponse;
import com.wanted.preonboarding.jobpost.dto.response.JobPostResponse;
import com.wanted.preonboarding.jobpost.service.JobPostService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/job-post")
    public ApiResponse<List<JobPostResponse>> retrieveJobPostList() {
        return ApiResponse.succeed(jobPostService.retrieveJobPostList());
    }

    @GetMapping("/job-post/{jobPostId}")
    public ApiResponse<JobPostDetailResponse> retrieveJobPostDetail(@PathVariable Long jobPostId) {
        return ApiResponse.succeed(jobPostService.retrieveJobPostDetail(jobPostId));
    }

    @GetMapping("/job-post/search")
    public ApiResponse<List<JobPostResponse>> searchJobPost(@RequestParam(defaultValue = "") String keyword) {
        return ApiResponse.succeed(jobPostService.searchJobPost(keyword));
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
