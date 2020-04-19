package kr.side.dstar.web.dto;

import kr.side.dstar.domain.scrap.Scrap;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScrapSaveRequestDto {

    private String url;
    private String data;

    @Builder
    public ScrapSaveRequestDto(String url, String data) {
        this.url    = url;
        this.data   = data;
    }

    public Scrap toEntity() {
        return Scrap.builder()
                .url(url)
                .data(data)
                .build();
    }
}
