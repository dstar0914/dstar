package kr.side.dstar.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MemberStatus {
    AUTHORIZED("AUTHORIZED", "승인"),
    UNAUTHORIZED("UNAUTHORIZED", "미승인");

    private final String key;
    private final String title;
}
