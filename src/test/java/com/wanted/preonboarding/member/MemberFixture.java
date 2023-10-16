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
        if (MEMBER_CHULSOO.getId() == null) {
            ReflectionTestUtils.setField(MEMBER_CHULSOO, "id", 1L);
        }
        return MEMBER_CHULSOO;
    }

    public static Member MEMBER_GILDONG() {
        if (MEMBER_GILDONG.getId() == null) {
            ReflectionTestUtils.setField(MEMBER_GILDONG, "id", 2L);
        }
        return MEMBER_GILDONG;
    }
}