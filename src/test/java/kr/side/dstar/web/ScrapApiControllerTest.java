package kr.side.dstar.web;

import kr.side.dstar.domain.scrap.Scrap;
import kr.side.dstar.domain.scrap.ScrapRepository;
import kr.side.dstar.web.dto.ScrapSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScrapApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ScrapRepository scrapRepository;

    @After
    public void clearAll() throws Exception {
        scrapRepository.deleteAll();
    }

    @Test
    public void save() throws Exception {
        String url  = "http://naver.com";
        String data = "<html></html>";

        ScrapSaveRequestDto requestDto = ScrapSaveRequestDto.builder()
                .url(url)
                .data(data)
                .build();

        String testUrl = "http://localhost:"+port+"/api/scrap";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(testUrl, requestDto, Long.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Scrap> all = scrapRepository.findAll();
        assertThat(all.get(0).getUrl()).isEqualTo(url);
        assertThat(all.get(0).getData()).isEqualTo(data);
    }
}