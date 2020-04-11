package kr.side.dstar.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScrapUpdateRequestDto {
    private String url;
    private String data;

    @Builder
    public ScrapUpdateRequestDto(String url, String data) {
        this.url    = url;
        this.data   = data;
    }
}
