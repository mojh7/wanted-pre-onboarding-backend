package com.wanted.preonboarding.applyjob.controller;

import com.wanted.preonboarding.applyjob.dto.request.ApplyJobCreateRequest;
import com.wanted.preonboarding.applyjob.service.ApplyJobService;
import com.wanted.preonboarding.common.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ApplyJobController {
    private final ApplyJobService applyJobService;

    public ApplyJobController(final ApplyJobService applyJobService) {
        this.applyJobService = applyJobService;
    }

    @PostMapping("/job-post/apply")
    public ApiResponse<?> applyJobPost(@RequestBody @Valid ApplyJobCreateRequest applyJobCreateRequest) {
        applyJobService.applyJobPost(applyJobCreateRequest);
        return ApiResponse.succeed();
    }
}