package kr.side.dstar.service.scrap;

import kr.side.dstar.domain.scrap.Scrap;
import kr.side.dstar.domain.scrap.ScrapRepository;
import kr.side.dstar.web.dto.ScrapListReponseDto;
import kr.side.dstar.web.dto.ScrapResponseDto;
import kr.side.dstar.web.dto.ScrapSaveRequestDto;
import kr.side.dstar.web.dto.ScrapUpdateRequestDto;
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
    public Long save(ScrapSaveRequestDto requestDto) {
        return scrapRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, ScrapUpdateRequestDto requestDto) {
        Scrap scrap = scrapRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id) );

        scrap.update(requestDto.getUrl(), requestDto.getData());

        return id;
    }

    @Transactional
    public void delete(Long id) {
        Scrap scrap = scrapRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id) );

        scrapRepository.delete(scrap);
    }

    public ScrapResponseDto findById(Long id) {
        Scrap entity = scrapRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id) );

        return new ScrapResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<ScrapListReponseDto> findAllDesc() {
        return scrapRepository.findAllDesc().stream()
                .map(ScrapListReponseDto::new)
                .collect(Collectors.toList());
    }
}
