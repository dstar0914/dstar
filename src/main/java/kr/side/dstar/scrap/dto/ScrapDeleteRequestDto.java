package kr.side.dstar.scrap.dto;

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
