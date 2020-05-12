package kr.side.dstar.scrap.dto;

import kr.side.dstar.scrap.Scrap;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScrapListReponseDto {
    private Long id;
    private String url;
    private String data;
    private LocalDateTime createdAt;

    public ScrapListReponseDto(Scrap entity) {
        this.id         = entity.getId();
        this.url        = entity.getUrl();
        this.data       = entity.getData();
        this.createdAt  = entity.getCreatedAt();
    }
}
