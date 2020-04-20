package kr.side.dstar.domain.scrap;

import kr.side.dstar.web.ScrapApiController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class ScrapResource extends EntityModel<ScrapResponseDto> {

    public ScrapResource(ScrapResponseDto responseDto, Link... links) {
        super(responseDto, links);
        add(linkTo(ScrapApiController.class).slash(responseDto.getId()).withSelfRel());
    }

    public ScrapResource(Scrap scrap) {
        super(new ScrapResponseDto(scrap));

        add(linkTo(ScrapApiController.class).slash(scrap.getId()).withSelfRel());
    }
}
