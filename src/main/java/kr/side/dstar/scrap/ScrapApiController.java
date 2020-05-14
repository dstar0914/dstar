package kr.side.dstar.scrap;

import kr.side.dstar.response.ResponseService;
import kr.side.dstar.scrap.dto.ScrapSaveRequestDto;
import kr.side.dstar.scrap.dto.ScrapUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/scrap")
public class ScrapApiController {

    private final ScrapService scrapService;
    private final ResponseService responseService;

    @PostMapping
    public ResponseEntity save(@RequestBody ScrapSaveRequestDto requestDto) {
        return ResponseEntity.ok(responseService.getSingleResult(scrapService.save(requestDto)));
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody ScrapUpdateRequestDto requestDto) {
        return ResponseEntity.ok(responseService.getSingleResult(scrapService.update(id, requestDto)));
    }

    @DeleteMapping("{id}")
    public Long delete(@PathVariable Long id) {
        scrapService.delete(id);
        return id;
    }

    @GetMapping("{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        return ResponseEntity.ok(responseService.getSingleResult(scrapService.findById(id)));
    }

    @GetMapping
    public ResponseEntity getList() {
        return ResponseEntity.ok(responseService.getListResult(scrapService.findAllDesc()));
    }
}
