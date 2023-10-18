package com.wanted.preonboarding.jobpost.dto.request;

import com.wanted.preonboarding.company.entity.Company;
import com.wanted.preonboarding.jobpost.entity.JobPost;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class JobPostCreateRequest {

    @Min(0)
    @Max(Long.MAX_VALUE)
    @NotNull
    private Long companyId;

    @NotBlank
    @Size(max = 64)
    private String position;

    @Min(0)
    @Max(Long.MAX_VALUE)
    private Long reward;

    @NotBlank
    @Size(max = 255)
    private String skills;

    @NotBlank
    @Size(max = 2000)
    private String description;

    public JobPostCreateRequest(Long companyId, String position, Long reward,
                                String skills, String description) {
        this.companyId = companyId;
        this.position = position;
        this.reward = reward;
        this.skills = skills;
        this.description = description;
    }

    public JobPost toEntity(final Company company) {
        return JobPost.builder()
                      .company(company)
                      .position(position)
                      .reward(reward)
                      .skills(skills)
                      .description(description)
                      .build();
    }
}
