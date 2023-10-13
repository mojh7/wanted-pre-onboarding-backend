package com.wanted.preonboarding.company;

import com.wanted.preonboarding.company.entity.Company;
import org.springframework.test.util.ReflectionTestUtils;

public class CompanyFixture {

    public static Company companyWanted() {
        Company company = Company.builder()
                                 .name("원티드랩")
                                 .nation("한국")
                                 .region("서울")
                                 .build();
        ReflectionTestUtils.setField(company, "id", 1L);
        return company;
    }
}
