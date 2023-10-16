package com.wanted.preonboarding.applyjob.dto.request;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
public class ApplyJobCreateRequest {
    @Min(0)
    @Max(Long.MAX_VALUE)
    private Long jobPostId;

    @Min(0)
    @Max(Long.MAX_VALUE)
    private Long memberId;

    public ApplyJobCreateRequest(Long jobPostId, Long memberId) {
        this.jobPostId = jobPostId;
        this.memberId = memberId;
    }
}
