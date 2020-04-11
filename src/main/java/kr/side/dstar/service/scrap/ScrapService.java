package kr.side.dstar.service.scrap;

import kr.side.dstar.domain.scrap.ScrapRepository;
import kr.side.dstar.web.dto.ScrapSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ScrapService {
    private final ScrapRepository scrapRepository;

    @Transactional
    public Long save(ScrapSaveRequestDto requestDto) {
        return scrapRepository.save(requestDto.toEntity()).getId();
    }
}
