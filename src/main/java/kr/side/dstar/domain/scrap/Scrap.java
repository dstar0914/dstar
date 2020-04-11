package kr.side.dstar.domain.scrap;

import kr.side.dstar.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public void update(String url, String data) {
        this.url    = url;
        this.data   = data;
    }
}
