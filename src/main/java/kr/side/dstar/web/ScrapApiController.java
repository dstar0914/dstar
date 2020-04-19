package kr.side.dstar.web;

import kr.side.dstar.domain.scrap.ScrapResource;
import kr.side.dstar.service.scrap.ScrapService;
import kr.side.dstar.web.dto.ScrapSaveRequestDto;
import kr.side.dstar.web.dto.ScrapUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ScrapApiController {
    private final ScrapService scrapService;

    @PostMapping("/api/scrap")
    public ScrapResource save(@RequestBody ScrapSaveRequestDto requestDto) {
        ScrapResource result = scrapService.save(requestDto);

        return scrapService.save(requestDto);
    }

    @PutMapping("/api/scrap/{id}")
    public ScrapResource update(@PathVariable Long id, @RequestBody ScrapUpdateRequestDto requestDto) {
        return scrapService.update(id, requestDto);
    }

    @DeleteMapping("/api/scrap/{id}")
    public Long delete(@PathVariable Long id) {
        scrapService.delete(id);
        return id;
    }

    @GetMapping("/api/scrap/{id}")
    public ScrapResource findById(@PathVariable Long id) {
        return scrapService.findById(id);
    }
}
