package kr.side.dstar.domain.scrap;

import kr.side.dstar.domain.BaseTimeEntity;
import kr.side.dstar.web.dto.ScrapResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Scrap extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String data;

    private Long userId;

    @Builder
    public Scrap(String url, String data) {
        this.url    = url;
        this.data   = data;
    }

    public ScrapResponseDto update(String url, String data) {
        this.url    = url;
        this.data   = data;

        ScrapResponseDto responseDto = new ScrapResponseDto(this);

        return responseDto;
    }
}
