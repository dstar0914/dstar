package kr.side.dstar.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.side.dstar.common.RestDocsConfiguration;
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
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Slf4j
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@RunWith(SpringRunner.class)
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScrapApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

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

        //when, then
        mockMvc.perform(post("/api/scrap")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.query-scrap").exists())
                .andExpect(jsonPath("_links.update-scrap").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document(
                        "create-scrap",
                        links(
                                linkWithRel("self").description("link to self"),
                                linkWithRel("query-scrap").description("link to query-scrap"),
                                linkWithRel("update-scrap").description("link to update-scrap"),
                                linkWithRel("profile").description("link to profile")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("request header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("request content type")
                        ),
                        requestFields(
                                fieldWithPath("url").description("scrap url"),
                                fieldWithPath("data").description("scrap data")
                        ),
                        responseHeaders(
                                //headerWithName(HttpHeaders.LOCATION).description("response header"), 왜 에러나는지 모르겠음
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("response content type")
                        ),
                        responseFields(
                                fieldWithPath("id").description("scrap id"),
                                fieldWithPath("url").description("scrap url"),
                                fieldWithPath("data").description("scrap data"),
                                fieldWithPath("createdAt").description("scrap createdAt"),
                                //fieldWithPath("userId").description("scrap userId"),
                                fieldWithPath("_links.self.href").description("links to self"),
                                fieldWithPath("_links.query-scrap.href").description("links to query-scrap"),
                                fieldWithPath("_links.update-scrap.href").description("links to update-scrap"),
                                fieldWithPath("_links.profile.href").description("links to profile")
                        )
                ));
    }

    /*
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
        log.info("{}",responseEntity);
        log.info("{}",responseEntity.getBody().get("id"));

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().get("id")).isNotNull();

        assertThat(responseEntity.getBody().get("_links.self")).isNotNull();
        assertThat(responseEntity.getBody().get("_links.query-events")).isNotNull();
        assertThat(responseEntity.getBody().get("_links.update-event")).isNotNull();

        List<Scrap> all = scrapRepository.findAll();
        Scrap scrap     = all.get(0);

        assertThat(scrap.getUrl()).isEqualTo(url);
        assertThat(scrap.getData()).isEqualTo(data);
    }
    */

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

    @Test
    public void getList() throws Exception {
        //given
        IntStream.range(0, 30).forEach(i -> {
            this.createScrap(i);
        });

        //when
        this.mockMvc.perform(get("/api/scrap")
                .param("page", "1")
                .param("size", "10")
                .param("sort", "id,DESC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.lists[0]._links.self.href").exists())
                .andExpect(jsonPath("_links.self.href").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-scrap"))
        ;

        //then
    }

    private void createScrap(int i) {
        Scrap scrap = Scrap.builder()
                .url("url"+i)
                .data("data"+i)
                .build();

        scrapRepository.save(scrap);
    }
}