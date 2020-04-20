package kr.side.dstar.domain.scrap;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ScrapResponseDto {

    private Long id;
    private String url;

    @Builder
    public ScrapResponseDto(Scrap scrap) {
        this.url = scrap.getUrl();
        this.id = scrap.getId();
    }

    public Scrap toEntity() {
        Scrap scrap = Scrap.builder()
                .url(url)
                .build();

        return scrap;
    }
}
