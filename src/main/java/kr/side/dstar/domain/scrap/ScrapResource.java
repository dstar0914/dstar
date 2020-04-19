package kr.side.dstar.domain.scrap;

import kr.side.dstar.web.ScrapApiController;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Getter
public class ScrapResource extends RepresentationModel {
    private Long id; // events 라는 키값으로 묶는 방법 찾아보기
    private String url;
    private String data;

    public ScrapResource(Scrap entity) {
        this.id = entity.getId();
        this.url = entity.getUrl();
        this.data = entity.getData();

        WebMvcLinkBuilder selfLinkBuilder = linkTo(ScrapApiController.class).slash(entity.getId());

        add(selfLinkBuilder.withSelfRel());
        add(selfLinkBuilder.withRel("query-scrap"));
        add(selfLinkBuilder.withRel("update-scrap"));
    }
}
