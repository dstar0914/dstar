package kr.side.dstar.scrap.dto;

import kr.side.dstar.scrap.Scrap;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Getter
@Relation(collectionRelation = "lists")
public class ScrapResponseDto {

    private Long id;
    private String url;
    private String data;
    private LocalDateTime createdAt;

    @Builder
    public ScrapResponseDto(Scrap scrap) {
        this.url        = scrap.getUrl();
        this.id         = scrap.getId();
        this.data       = scrap.getData();
        this.createdAt  = scrap.getCreatedAt();
    }
}
