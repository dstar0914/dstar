package kr.side.dstar.domain.scrap;

import kr.side.dstar.scrap.Scrap;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ScrapTest {
    /**
     * 빌더 적용 테스트
     */
    @Test
    public void builder() {
        Scrap scrap = Scrap.builder()
                .url("url")
                .data("data")
                .build();
        assertThat(scrap).isNotNull();
    }
}