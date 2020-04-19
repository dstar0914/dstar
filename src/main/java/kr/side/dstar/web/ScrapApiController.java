package kr.side.dstar.web;

import kr.side.dstar.domain.scrap.ScrapResource;
import kr.side.dstar.service.scrap.ScrapService;
import kr.side.dstar.web.dto.ScrapSaveRequestDto;
import kr.side.dstar.web.dto.ScrapUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/scrap", produces = MediaTypes.HAL_JSON_VALUE)
public class ScrapApiController {
    private final ScrapService scrapService;

    @PostMapping()
    public ScrapResource save(@RequestBody ScrapSaveRequestDto requestDto) {
        ScrapResource result = scrapService.save(requestDto);

        return scrapService.save(requestDto);
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
}
