package kr.side.dstar.web;

import kr.side.dstar.service.scrap.ScrapService;
import kr.side.dstar.web.dto.ScrapResponseDto;
import kr.side.dstar.web.dto.ScrapSaveRequestDto;
import kr.side.dstar.web.dto.ScrapUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ScrapApiController {
    private final ScrapService scrapService;

    @PostMapping("/api/scrap")
    public Long save(@RequestBody ScrapSaveRequestDto requestDto) {
        return scrapService.save(requestDto);
    }

    @PutMapping("/api/scrap/{id}")
    public Long update(@PathVariable Long id, @RequestBody ScrapUpdateRequestDto requestDto) {
        return scrapService.update(id, requestDto);
    }

    @GetMapping("/api/scrap/{id}")
    public ScrapResponseDto findById(@PathVariable Long id) {
        return scrapService.findById(id);
    }
}
