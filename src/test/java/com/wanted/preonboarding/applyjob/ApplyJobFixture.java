package com.wanted.preonboarding.applyjob;

import com.wanted.preonboarding.applyjob.dto.request.ApplyJobCreateRequest;
import com.wanted.preonboarding.applyjob.entity.ApplyJob;
import com.wanted.preonboarding.jobpost.JobPostFixture;
import com.wanted.preonboarding.member.MemberFixture;
import org.springframework.test.util.ReflectionTestUtils;

public class ApplyJobFixture {

    private static ApplyJob APPLY_JOB1 = ApplyJob.builder()
                                               .jobPost(JobPostFixture.JOBPOST_WANTED1())
                                               .member(MemberFixture.MEMBER_CHULSOO())
                                               .build();

    public static ApplyJobCreateRequest APPLY_JOB_CREATE_REQUEST1 = new ApplyJobCreateRequest(
            JobPostFixture.JOBPOST_WANTED1().getId(), MemberFixture.MEMBER_CHULSOO().getId());


    public static ApplyJob APPLY_JOB1() {
        if (APPLY_JOB1.getId() == null) {
            ReflectionTestUtils.setField(APPLY_JOB1, "id", 1L);
        }
        return APPLY_JOB1;
    }
}
