package kr.side.dstar.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.side.dstar.domain.scrap.Scrap;
import kr.side.dstar.domain.scrap.ScrapRepository;
import kr.side.dstar.web.dto.ScrapSaveRequestDto;
import kr.side.dstar.web.dto.ScrapUpdateRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScrapApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    ObjectMapper objectMapper;

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
        //given
        String url  = "http://naver.com";
        String data = "<html></html>";

        ScrapSaveRequestDto requestDto = ScrapSaveRequestDto.builder()
                                        .url(url)
                                        .data(data)
                                        .build();

        String testUrl = "http://localhost:"+port+"/api/scrap";

        //when
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(testUrl, requestDto, Map.class);
        log.info("{}",responseEntity.getBody());
        log.info("{}",responseEntity.getBody().get("id"));

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().get("id")).isNotNull();

        List<Scrap> all = scrapRepository.findAll();
        Scrap scrap     = all.get(0);

        assertThat(scrap.getUrl()).isEqualTo(url);
        assertThat(scrap.getData()).isEqualTo(data);
    }

    @Test
    public void update() throws Exception {
        //given
        Scrap savedScrap = scrapRepository.save(Scrap.builder()
                            .url("http://google.com")
                            .data("<head></head>")
                            .build());

        Long updateId       = savedScrap.getId();

        String expectedUrl  = "url";
        String expectedData = "data";

        ScrapUpdateRequestDto requestDto = ScrapUpdateRequestDto.builder()
                .url(expectedUrl)
                .data(expectedData)
                .build();

        String testUrl      = "http://localhost:"+port+"/api/scrap/"+updateId;

        //when
        HttpEntity<ScrapUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(testUrl, HttpMethod.PUT, requestEntity, Map.class);
        log.info("{}", responseEntity);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().get("id")).isNotNull();

        List<Scrap> all = scrapRepository.findAll();
        Scrap scrap     = all.get(0);

        assertThat(scrap.getUrl()).isEqualTo(expectedUrl);
        assertThat(scrap.getData()).isEqualTo(expectedData);
    }

    @Test
    public void delete() throws Exception {
        //given
        Scrap savedScrap = scrapRepository.save(Scrap.builder()
                        .url("deleteurl")
                        .data("deletedata")
                        .build());

        Long deleteId = savedScrap.getId();

        String testUrl = "http://localhost:"+port+"/api/scrap/"+deleteId;

        //when
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity(httpHeaders);

        ResponseEntity<Long> responseEntity = restTemplate.exchange(testUrl, HttpMethod.DELETE, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        Optional<Scrap> deletedScrap = scrapRepository.findById(deleteId);
        Assert.assertFalse(deletedScrap.isPresent());
    }
}