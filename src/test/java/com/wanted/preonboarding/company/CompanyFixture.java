package com.wanted.preonboarding.company;

import com.wanted.preonboarding.company.entity.Company;
import org.springframework.test.util.ReflectionTestUtils;

public class CompanyFixture {

    private static Company COMPANY_WANTED = Company.builder()
                                                   .name("원티드랩")
                                                   .nation("한국")
                                                   .region("서울")
                                                   .build();

    private static Company COMPANY_NAVER = Company.builder()
                                                  .name("네이버")
                                                  .nation("한국")
                                                  .region("경기도")
                                                  .build();

    public static Company COMPANY_WANTED() {
        if (COMPANY_WANTED.getId() == null) {
            ReflectionTestUtils.setField(COMPANY_WANTED, "id", 1L);
        }
        return COMPANY_WANTED;
    }

    public static Company COMPANY_NAVER() {
        if (COMPANY_NAVER.getId() == null) {
            ReflectionTestUtils.setField(COMPANY_NAVER, "id", 2L);
        }
        return COMPANY_NAVER;
    }
}
