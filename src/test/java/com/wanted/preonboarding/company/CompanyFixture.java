package com.wanted.preonboarding.company;

import com.wanted.preonboarding.company.entity.Company;
import com.wanted.preonboarding.jobpost.entity.JobPost;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

public class CompanyFixture {

    private static Company COMPANY_WANTED = Company.builder()
                                                   .name("원티드랩")
                                                   .nation("한국")
                                                   .region("서울")
                                                   .build();

    private static Company COMPANY_NAVER = Company.builder()
                                                  .name("네이버")
                                                  .nation("한국")
                                                  .region("판교")
                                                  .build();

    private static Company COMPANY_NEXON = Company.builder()
                                                  .name("넥슨")
                                                  .nation("한국")
                                                  .region("판교")
                                                  .build();

    public static Company COMPANY_WANTED() {
        Company company = COMPANY_WANTED;
        if (company.getId() == null) {
            ReflectionTestUtils.setField(company, "id", 1L);
        }
        return company;
    }

    public static Company COMPANY_NAVER() {
        Company company = COMPANY_NAVER;
        if (company.getId() == null) {
            ReflectionTestUtils.setField(company, "id", 2L);
        }
        return company;
    }

    /**
     * <p>{@link JobPost} 와 {@link Company}가 N:1 연관 관계이고 만약 Company가 JobPost1, JobPost2, JobPost3를 생성했을 때
     *
     * <p>JobPost1 초기화시 Company가 필요해서 CompanyFixture.COMPANY_XXX()를 호출
     *
     * <p>Company 초기화할 때 JobPostList에 JobPost1, 2, 3 데이터를 저장하는데 JobPost2, 3는 아직 생성하지 않았고
     * 생성하기 위해서는 또 다시 Company가 필요해서 {@link java.lang.ExceptionInInitializerError} 발생
     *
     * <p>회사가 올린 다른 채용 공고(JobPostList)를 사용하는 테스트를 위해 Fixture에 선언된 JobPost가 아닌
     * 바로 JobPost를 생성해서 JobPostList 셋팅한 Company entity class
     * @return 회사 이름 Nexon인 Company entity
     */
    public static Company COMPANY_NEXON() {
        Company company = COMPANY_NEXON;
        if (company.getId() == null) {
            ReflectionTestUtils.setField(company, "id", 3L);

            JobPost jobPost10500 = JobPost.builder()
                                          .company(company)
                                          .position("게임 클라이언트 개발자")
                                          .reward(1500000L)
                                          .skills("C#, Unity")
                                          .description("넥슨에서 게임 클라이언트 개발자를 채용합니다.")
                                          .build();

            JobPost deletedJobPost10501 = JobPost.builder()
                                                 .company(company)
                                                 .position("게임 서버 개발자")
                                                 .reward(1500000L)
                                                 .skills("C++")
                                                 .description("넥슨에서 게임 서버 개발자를 채용합니다.")
                                                 .build();

            JobPost jobPost10502 = JobPost.builder()
                                          .company(company)
                                          .position("웹 백엔드 엔지니어")
                                          .reward(1500000L)
                                          .skills("C#, ASP.NET")
                                          .description("넥슨에서 웹 백엔드 엔지니어를 채용합니다.")
                                          .build();

            ReflectionTestUtils.setField(jobPost10500, "id", 10500L);
            ReflectionTestUtils.setField(deletedJobPost10501, "id", 10501L);
            ReflectionTestUtils.setField(deletedJobPost10501, "isDeleted", true);
            ReflectionTestUtils.setField(jobPost10502, "id", 10502L);

            ReflectionTestUtils.setField(company, "jobPostList", Arrays.asList(
                    jobPost10500, deletedJobPost10501, jobPost10502
            ));
        }
        return company;
    }
}
