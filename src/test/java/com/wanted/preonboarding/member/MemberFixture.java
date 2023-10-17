package com.wanted.preonboarding.member;

import com.wanted.preonboarding.member.entity.Member;
import org.springframework.test.util.ReflectionTestUtils;

public class MemberFixture {

    private static Member MEMBER_CHULSOO = Member.builder()
                                                 .name("김철수")
                                                 .build();

    private static Member MEMBER_GILDONG = Member.builder()
                                                 .name("홍길동")
                                                 .build();

    public static Member MEMBER_CHULSOO() {
        Member member = MEMBER_CHULSOO;
        if (member.getId() == null) {
            ReflectionTestUtils.setField(member, "id", 1L);
        }
        return member;
    }

    public static Member MEMBER_GILDONG() {
        Member member = MEMBER_GILDONG;
        if (member.getId() == null) {
            ReflectionTestUtils.setField(member, "id", 2L);
        }
        return member;
    }
}