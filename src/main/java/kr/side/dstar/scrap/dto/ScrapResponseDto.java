package kr.side.dstar.scrap.dto;

import kr.side.dstar.scrap.Scrap;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScrapResponseDto {

    private Long id;
    private String url;
    private String data;
    private LocalDateTime createdAt;

    public ScrapResponseDto(Scrap scrap) {
        this.url        = scrap.getUrl();
        this.id         = scrap.getId();
        this.data       = scrap.getData();
        this.createdAt  = scrap.getCreatedAt();
    }
}
