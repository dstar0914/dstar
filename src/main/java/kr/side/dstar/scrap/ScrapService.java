package kr.side.dstar.scrap;

import kr.side.dstar.scrap.dto.ScrapListReponseDto;
import kr.side.dstar.scrap.dto.ScrapResponseDto;
import kr.side.dstar.scrap.dto.ScrapSaveRequestDto;
import kr.side.dstar.scrap.dto.ScrapUpdateRequestDto;
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
        Scrap saveScrap = scrapRepository.save(requestDto.toEntity());

        return new ScrapResponseDto(saveScrap);
    }

    @Transactional
    public ScrapResponseDto update(Long id, ScrapUpdateRequestDto requestDto) {
        Scrap scrap = scrapRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id) );

        return scrap.update(requestDto.getUrl(), requestDto.getData());
        /* scrap 자체를 리턴해보기 */
    }

    @Transactional
    public void delete(Long id) {
        Scrap scrap = scrapRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 스크랩이 없습니다. id="+id) );

        scrapRepository.delete(scrap);
    }

    @Transactional(readOnly = true)
    public ScrapResponseDto findById(Long id) {
        Scrap entity = scrapRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 스크랩이 없습니다. id="+id) );

        return new ScrapResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<ScrapListReponseDto> findAllDesc() {
        List<ScrapListReponseDto> result =  scrapRepository.findAllDesc().stream()
                .map(ScrapListReponseDto::new)
                .collect(Collectors.toList());

        return result;
    }
}
