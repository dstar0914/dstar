package kr.side.dstar.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    AUTHORIZED("ROLE_AUTHORIZED", "승인"),
    UNAUTHORIZED("ROLE_UNAUTHORIZED", "미승인");

    private final String key;
    private final String title;
}
