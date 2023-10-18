package com.wanted.preonboarding.applyjob.dto.request;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class ApplyJobCreateRequest {

    @Min(0)
    @Max(Long.MAX_VALUE)
    @NotNull
    private Long jobPostId;


    @Min(0)
    @Max(Long.MAX_VALUE)
    @NotNull
    private Long memberId;

    public ApplyJobCreateRequest(Long jobPostId, Long memberId) {
        this.jobPostId = jobPostId;
        this.memberId = memberId;
    }
}
