package kr.side.dstar.token.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TokenResponseDto {

    private String accessToken;
    private String refreshToken;
    private Long expire;

    @Builder
    public TokenResponseDto(String accessToken, String refreshToken, Long expire) {
        this.accessToken   = accessToken;
        this.refreshToken  = refreshToken;
        this.expire         = expire;
    }
}
