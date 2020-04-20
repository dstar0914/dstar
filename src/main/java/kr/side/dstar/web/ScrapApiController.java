package kr.side.dstar.web;

import kr.side.dstar.domain.scrap.Scrap;
import kr.side.dstar.domain.scrap.ScrapRepository;
import kr.side.dstar.domain.scrap.ScrapResource;
import kr.side.dstar.service.scrap.ScrapService;
import kr.side.dstar.web.dto.ScrapSaveRequestDto;
import kr.side.dstar.web.dto.ScrapUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/scrap", produces = MediaTypes.HAL_JSON_VALUE)
public class ScrapApiController {
    private final ScrapService scrapService;
    private final ScrapRepository scrapRepository;

    @PostMapping()
    public ResponseEntity save(@RequestBody ScrapSaveRequestDto requestDto) {
        Scrap createScrap = scrapService.save(requestDto);

        ScrapResource scrapResource = new ScrapResource(createScrap);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(ScrapApiController.class).slash(createScrap.getId());
        URI createdUri = selfLinkBuilder.toUri();

        scrapResource.add(selfLinkBuilder.withRel("query-scrap"));
        scrapResource.add(selfLinkBuilder.withRel("update-scrap"));
        scrapResource.add(new Link("/docs/index.html#resources-scrap-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(scrapResource);
    }

    @PutMapping("{id}")
    public ScrapResource update(@PathVariable Long id, @RequestBody ScrapUpdateRequestDto requestDto) {
        return scrapService.update(id, requestDto);
    }

    @DeleteMapping("{id}")
    public Long delete(@PathVariable Long id) {
        scrapService.delete(id);
        return id;
    }

    @GetMapping("{id}")
    public ScrapResource findById(@PathVariable Long id) {
        return scrapService.findById(id);
    }

    @GetMapping
    public ResponseEntity getList(Pageable pageable, PagedResourcesAssembler<Scrap> assembler) {
        PagedModel<EntityModel<Scrap>> pageResource = assembler.toModel(scrapRepository.findAll(pageable), ScrapResource::new);

        pageResource.add(new Link("/docs/index.html#resources-scrap-list").withRel("profile"));

        return ResponseEntity.ok(pageResource);
    }
}
