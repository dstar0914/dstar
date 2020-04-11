package kr.side.dstar.web;

import kr.side.dstar.service.scrap.ScrapService;
import kr.side.dstar.web.dto.ScrapSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ScrapApiController {
    private final ScrapService scrapService;

    @PostMapping("/api/scrap")
    public Long save(@RequestBody ScrapSaveRequestDto requestDto) {
        return scrapService.save(requestDto);
    }
}
