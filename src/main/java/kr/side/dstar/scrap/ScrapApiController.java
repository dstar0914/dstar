package kr.side.dstar.scrap;

import kr.side.dstar.response.ResponseService;
import kr.side.dstar.scrap.dto.ScrapSaveRequestDto;
import kr.side.dstar.scrap.dto.ScrapUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
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
    private final ResponseService responseService;

    @PostMapping()
    public ResponseEntity save(@RequestBody ScrapSaveRequestDto requestDto) {
        ScrapResponseDto createScrap = scrapService.save(requestDto);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(ScrapApiController.class).slash(createScrap.getId());
        URI createdUri = selfLinkBuilder.toUri();

        return ResponseEntity.created(createdUri).body(responseService.getSingleResult(createScrap));
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody ScrapUpdateRequestDto requestDto) {
        return ResponseEntity.ok(scrapService.update(id, requestDto));
    }

    @DeleteMapping("{id}")
    public Long delete(@PathVariable Long id) {
        scrapService.delete(id);
        return id;
    }

    @GetMapping("{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        return ResponseEntity.ok(scrapService.findById(id));
    }

    @GetMapping
    public ResponseEntity getList() {
        return ResponseEntity.ok(scrapService.findAllDesc());
    }
}
