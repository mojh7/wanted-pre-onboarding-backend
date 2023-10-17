package com.wanted.preonboarding.jobpost;

import com.wanted.preonboarding.company.CompanyFixture;
import com.wanted.preonboarding.jobpost.dto.request.JobPostCreateRequest;
import com.wanted.preonboarding.jobpost.dto.request.JobPostUpdateRequest;
import com.wanted.preonboarding.jobpost.entity.JobPost;
import org.springframework.test.util.ReflectionTestUtils;


public class JobPostFixture {

    public static JobPostCreateRequest JOBPOST_CREATE_REQUEST_WANTED1 = new JobPostCreateRequest(
            1L, "백엔드 주니어 개발자", 1000000L,
            "Python", "원티드랩에서 백엔드 주니어 개발자를 채용합니다.");

    public static JobPostUpdateRequest JOBPOST_UPDATE_REQUEST_WANTED1 = new JobPostUpdateRequest(
            1L, "백엔드 주니어 개발자", 1500000L,
            "Python", "원티드랩에서 백엔드 주니어 개발자를 채용합니다.");

    public static JobPostUpdateRequest JOBPOST_UPDATE_REQUEST_NAVER1 = new JobPostUpdateRequest(
            2L, "백엔드 개발자", 1000000L,
            "Java", "네이버에서 백엔드 개발자(신입/경력)를 채용합니다.");

    private static JobPost JOBPOST_WANTED1 = JobPost.builder()
                                                    .company(CompanyFixture.COMPANY_WANTED())
                                                    .position("백엔드 주니어 개발자")
                                                    .reward(1000000L)
                                                    .skills("Python")
                                                    .description("원티드랩에서 백엔드 주니어 개발자를 채용합니다.")
                                                    .build();

    private static JobPost JOBPOST_WANTED2 = JobPost.builder()
                                                    .company(CompanyFixture.COMPANY_WANTED())
                                                    .position("프론트엔드 주니어 개발자")
                                                    .reward(1000000L)
                                                    .skills("Vue")
                                                    .description("원티드랩에서 프론트엔드 주니어 개발자를 채용합니다.")
                                                    .build();

    private static JobPost JOBPOST_NAVER1 = JobPost.builder()
                                                    .company(CompanyFixture.COMPANY_NAVER())
                                                    .position("백엔드 개발자")
                                                    .reward(500000L)
                                                    .skills("Java")
                                                    .description("네이버에서 백엔드 개발자(신입/경력)를 채용합니다.")
                                                    .build();

    private static JobPost JOBPOST_NEXON1 = JobPost.builder()
                                                   .company(CompanyFixture.COMPANY_NEXON())
                                                   .position("게임 클라이언트 개발자")
                                                   .reward(1500000L)
                                                   .skills("C#, Unity")
                                                   .description("넥슨에서 게임 클라이언트 개발자를 채용합니다.")
                                                   .build();

    private static JobPost DELETED_JOBPOST_NEXON2 = JobPost.builder()
                                                           .company(CompanyFixture.COMPANY_NEXON())
                                                           .position("게임 클라이언트 개발자")
                                                           .reward(1500000L)
                                                           .skills("C#, Unity")
                                                           .description("넥슨에서 게임 클라이언트 개발자를 채용합니다.")
                                                           .build();

    private static JobPost DELETED_JOBPOST_NAVER2 = JobPost.builder()
                                                   .company(CompanyFixture.COMPANY_NAVER())
                                                   .position("iOS 개발자")
                                                   .reward(100000L)
                                                   .skills("Swift")
                                                   .description("네이버에서 iOS 개발자를 채용합니다.")
                                                   .build();


    public static JobPost JOBPOST_WANTED1() {
        JobPost jobPost = JOBPOST_WANTED1;
        if (jobPost.getId() == null) {
            ReflectionTestUtils.setField(jobPost, "id", 1L);
        }
        return jobPost;
    }

    public static JobPost JOBPOST_WANTED2() {
        JobPost jobPost = JOBPOST_WANTED2;
        if (jobPost.getId() == null) {
            ReflectionTestUtils.setField(jobPost, "id", 3L);
        }
        return jobPost;
    }

    public static JobPost JOBPOST_NEXON1() {
        JobPost jobPost = JOBPOST_NEXON1;
        if (jobPost.getId() == null) {
            ReflectionTestUtils.setField(jobPost, "id", 10500L);
        }
        return jobPost;
    }

    public static JobPost DELETED_JOBPOST_NEXON2() {
        JobPost jobPost = DELETED_JOBPOST_NEXON2;
        if (jobPost.getId() == null) {
            ReflectionTestUtils.setField(jobPost, "id", 10501L);
            ReflectionTestUtils.setField(jobPost, "isDeleted", true);
        }
        return jobPost;
    }


    public static JobPost JOBPOST_NAVER1() {
        JobPost jobPost = JOBPOST_NAVER1;
        if (jobPost.getId() == null) {
            ReflectionTestUtils.setField(jobPost, "id", 2L);
        }
        return jobPost;
    }

    public static JobPost DELETED_JOBPOST_NAVER2() {
        JobPost jobPost = DELETED_JOBPOST_NAVER2;
        if (jobPost.getId() == null) {
            ReflectionTestUtils.setField(jobPost, "id", 4L);
            ReflectionTestUtils.setField(jobPost, "isDeleted", true);
        }
        return jobPost;
    }
}
