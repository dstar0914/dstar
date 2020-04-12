package kr.side.dstar.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ScrapDeleteRequestDto {
    private Long id;

    public ScrapDeleteRequestDto(Long id) {
        this.id = id;
    }
}
