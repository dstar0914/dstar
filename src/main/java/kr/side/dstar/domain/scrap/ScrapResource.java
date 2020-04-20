package kr.side.dstar.domain.scrap;

import kr.side.dstar.web.ScrapApiController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class ScrapResource extends EntityModel<Scrap> {

    public ScrapResource(Scrap entity, Link... links) {
        super(entity, links);
        add(linkTo(ScrapApiController.class).slash(entity.getId()).withSelfRel());
    }
}
