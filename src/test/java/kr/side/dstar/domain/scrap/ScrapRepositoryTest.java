package kr.side.dstar.domain.scrap;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ScrapRepositoryTest {
    @Autowired
    ScrapRepository scrapRepository;

    @After
    public void cleanUp() {
        scrapRepository.deleteAll();
    }

    @Test
    public void save() {
        String url  = "http://naver.com";
        String data = "<html></html>";

        scrapRepository.save(Scrap.builder()
                    .url(url)
                    .data(data)
                    .build());

        List<Scrap> scrapList = scrapRepository.findAll();

        Scrap scrap = scrapList.get(0);
        assertThat(scrap.getUrl()).isEqualTo(url);
        assertThat(scrap.getData()).isEqualTo(data);
    }

    @Test
    public void baseTimeEntitySave() {
        LocalDateTime now = LocalDateTime.of(2019,6,4,0,0,0);

        scrapRepository.save(Scrap.builder()
                .url("url")
                .data("data")
                .build());

        List<Scrap> scrapList = scrapRepository.findAll();

        Scrap scrap = scrapList.get(0);

        System.out.println(">>>>> created="+scrap.getCreatedAt());

        assertThat(scrap.getCreatedAt()).isAfter(now);
    }
}