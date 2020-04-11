package kr.side.dstar.web.dto;

import kr.side.dstar.domain.scrap.Scrap;
import lombok.Getter;

@Getter
public class ScrapResponseDto {
    private Long id;
    private String url;
    private String data;

    public ScrapResponseDto(Scrap entity) {
        this.id     = entity.getId();
        this.url    = entity.getUrl();
        this.data   = entity.getData();
    }
}
