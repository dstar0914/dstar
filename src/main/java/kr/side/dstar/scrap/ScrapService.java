package kr.side.dstar.scrap;

import kr.side.dstar.scrap.dto.ScrapResponseDto;
import kr.side.dstar.scrap.dto.ScrapSaveRequestDto;
import kr.side.dstar.scrap.dto.ScrapUpdateRequestDto;
import kr.side.dstar.scrap.exception.CScrapNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ScrapService {

    private final ScrapRepository scrapRepository;

    @Transactional
    public ScrapResponseDto save(ScrapSaveRequestDto requestDto) {
        return new ScrapResponseDto(scrapRepository.save(requestDto.toEntity()));
    }

    @Transactional
    public ScrapResponseDto update(Long id, ScrapUpdateRequestDto requestDto) {
        Scrap scrap = scrapRepository.findById(id)
                .orElseThrow(CScrapNotFoundException::new);

        return scrap.update(requestDto.getUrl(), requestDto.getData());
    }

    @Transactional
    public void delete(Long id) {
        Scrap scrap = scrapRepository.findById(id)
                .orElseThrow(CScrapNotFoundException::new);

        scrapRepository.delete(scrap);
    }

    @Transactional(readOnly = true)
    public ScrapResponseDto getSingle(Long id) {
        Scrap entity = scrapRepository.findById(id)
                .orElseThrow(() -> new CScrapNotFoundException("custom error message"));

        return new ScrapResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<ScrapResponseDto> getList() {
        List<ScrapResponseDto> result =  scrapRepository.findAllDesc().stream()
                .map(ScrapResponseDto::new)
                .collect(Collectors.toList());

        return result;
    }
}
