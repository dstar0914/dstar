package kr.side.dstar.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.side.dstar.auth.dto.LoginRequestDto;
import kr.side.dstar.common.RestDocsConfiguration;
import kr.side.dstar.member.Member;
import kr.side.dstar.member.MemberRole;
import kr.side.dstar.member.MemberService;
import kr.side.dstar.member.MemberStatus;
import kr.side.dstar.scrap.Scrap;
import kr.side.dstar.scrap.ScrapRepository;
import kr.side.dstar.scrap.dto.ScrapSaveRequestDto;
import kr.side.dstar.scrap.dto.ScrapUpdateRequestDto;
import kr.side.dstar.token.JwtTokenProvider;
import kr.side.dstar.token.dto.TokenResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@RunWith(SpringRunner.class)
@Import(RestDocsConfiguration.class)
//@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScrapApiControllerTest {
//    @LocalServerPort
//    private int port;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

//    @Autowired
//    private TestRestTemplate restTemplate;
//
    @Autowired
    private ScrapRepository scrapRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

//    @After
//    public void clearAll() throws Exception {
//        scrapRepository.deleteAll();
//    }

    @Transactional
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
                .header("X-AUTH-TOKEN", getJwtToken())
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

//    @Transactional
//    @Test
//    public void authentication() throws Exception {
//        //given
//        String username = "ccc@email.com";
//        String password = "pass";
//
//        Member member = Member.builder()
//                .email(username)
//                .password(password)
//                .roles(Stream.of(Role.ADMIN).collect(Collectors.toSet()))
//                .status(MemberStatus.AUTHORIZED)
//                .build();
//
//        memberService.saveMember(member);
//
//        //when
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(username, password)
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        UserDetails user = memberService.loadUserByUsername(username);
//
//        log.info("{}", user.getAuthorities());
//
//        String jwt = jwtTokenProvider.createToken(member.getEmail(), user.getAuthorities());
//
//        //then
//        assertThat(jwt).isNotEmpty();
//        log.info(jwt);
//    }

    @Test
    public void auth() throws Exception {
        //given
        String username = "abc@email.com";
        String password = "pass";

        Member member = Member.builder()
                .email(username)
                .password(password)
                .status(MemberStatus.AUTHORIZED)
                .roles(Stream.of(MemberRole.ADMIN).collect(Collectors.toSet()))
                .build();

        memberService.saveMember(member);

        String clientId     = "myApp";
        String clientSecret = "pass";

        ResultActions perform = mockMvc.perform(post("/oauth/token")
                //.with(httpBasic(clientId, clientSecret))
                .param("username", username)
                .param("password", password)
                .param("grant_type", "password"));

        String responseBody =  perform.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();

        log.info("{}", perform.andReturn().getResponse());

        //return parser.parseMap(responseBody).get("access_token").toString();
    }

    public String getJwtToken() throws Exception {
        String username = "abc@email.com";
        String password = "pass";

        Member member = Member.builder()
                .email(username)
                .password(password)
                .roles(Stream.of(MemberRole.ADMIN).collect(Collectors.toSet()))
                .status(MemberStatus.AUTHORIZED)
                .build();

        memberService.saveMember(member);

//        Map<String, String> userInfo = new HashMap<>();
//        userInfo.put("username", username);
//        userInfo.put("password", password);

        LoginRequestDto requestDto = LoginRequestDto.builder()
                .email(username)
                .password(password)
                .build();

        ResultActions perform = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print());

        String responseBody =  perform.andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        TokenResponseDto token = mapper.readValue(responseBody, TokenResponseDto.class);

        log.info("==============================");
        log.info("token", token.getAccessToken());

        return token.getAccessToken();
    }

    private String getBearerToken() throws Exception {
        //given
        String username = "abc@email.com";
        String password = "pass";

        Member member = Member.builder()
                .email(username)
                .password(password)
                .status(MemberStatus.AUTHORIZED)
                .roles(Stream.of(MemberRole.ADMIN).collect(Collectors.toSet()))
                .build();

        memberService.saveMember(member);

        String clientId     = "myApp";
        String clientSecret = "pass";

        ResultActions perform = mockMvc.perform(post("/oauth/token")
                //.with(httpBasic(clientId, clientSecret))
                .param("username", username)
                .param("password", password)
                .param("grant_type", "password"));

        String responseBody =  perform.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();

        return parser.parseMap(responseBody).get("access_token").toString();
    }

    @Transactional
    @Test
    public void update() throws Exception {
        //given
        String url  = "http://naver.com";
        String data = "<html></html>";

        Scrap savedScrap = scrapRepository.save(Scrap.builder()
                .url(url)
                .data(data)
                .build());

        Long updateId       = savedScrap.getId();

        String expectedUrl  = "url";
        String expectedData = "data";

        ScrapUpdateRequestDto requestDto = ScrapUpdateRequestDto.builder()
                .url(expectedUrl)
                .data(expectedData)
                .build();

        //when, then
        mockMvc.perform(put("/api/scrap/{id}",updateId)
                //.header("X-AUTH-TOKEN", getJwtToken())
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+ getBearerToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.query-scrap").exists())
                .andExpect(jsonPath("_links.create-scrap").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document(
                        "update-scrap",
                        links(
                                linkWithRel("self").description("link to self"),
                                linkWithRel("query-scrap").description("link to query-scrap"),
                                linkWithRel("create-scrap").description("link to create-scrap"),
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
                                fieldWithPath("_links.create-scrap.href").description("links to update-scrap"),
                                fieldWithPath("_links.profile.href").description("links to profile")
                        )
                ));
    }
    
    @Test
    public void getScrap() throws Exception {
        //given
        String url  = "asd";
        String data = "<html></html>";

        Scrap savedScrap = scrapRepository.save(Scrap.builder()
                .url(url)
                .data(data)
                .build());

        Long savedId = savedScrap.getId();

        //when, then
        mockMvc.perform(get("/api/scrap/{id}", savedId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.query-scrap").exists())
                .andExpect(jsonPath("_links.update-scrap").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document(
                        "get-scrap",
                        links(
                                linkWithRel("self").description("link to self"),
                                linkWithRel("query-scrap").description("link to query-scrap"),
                                linkWithRel("update-scrap").description("link to create-scrap"),
                                linkWithRel("profile").description("link to profile")
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

    /*
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
    */

    @Transactional
    @Test
    public void delete() throws Exception {
        //given
        Scrap savedScrap = scrapRepository.save(Scrap.builder()
                .url("deleteurl")
                .data("deletedata")
                .build());

        Long deleteId = savedScrap.getId();

        //then
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/scrap/{id}", deleteId)
                //.header("X-AUTH-TOKEN", getJwtToken()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+ getBearerToken()))
                .andDo(print())
                .andExpect(status().isOk());
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
                .andDo(document(
                        "query-scrap",
                        links(
                                linkWithRel("self").description("link to self"),
//                                linkWithRel("query-scrap").description("link to query-scrap"),
//                                linkWithRel("update-scrap").description("link to update-scrap"),
                                linkWithRel("next").description("link to update-scrap"),
                                linkWithRel("last").description("link to update-scrap"),
                                linkWithRel("prev").description("link to update-scrap"),
                                linkWithRel("first").description("link to update-scrap"),
                                linkWithRel("profile").description("link to profile")
                        ),
                        responseHeaders(
                                //headerWithName(HttpHeaders.LOCATION).description("response header"), 왜 에러나는지 모르겠음
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("response content type")
                        ),
                        responseFields(
                                fieldWithPath("_embedded.lists[].id").description("scrap id"),
                                fieldWithPath("_embedded.lists[].url").description("scrap url"),
                                fieldWithPath("_embedded.lists[].data").description("scrap data"),
                                fieldWithPath("_embedded.lists[].createdAt").description("scrap createdAt"),
                                fieldWithPath("_embedded.lists[]_links.self.href").description("scrap createdAt"),
                                //fieldWithPath("userId").description("scrap userId"),
                                fieldWithPath("_links.self.href").description("links to self"),
//                                fieldWithPath("_links.query-scrap.href").description("links to query-scrap"),
//                                fieldWithPath("_links.update-scrap.href").description("links to update-scrap"),
                                fieldWithPath("_links.profile.href").description("links to profile"),
                                fieldWithPath("_links.next.href").description("links to next"),
                                fieldWithPath("_links.last.href").description("links to last"),
                                fieldWithPath("_links.prev.href").description("links to prev"),
                                fieldWithPath("_links.first.href").description("links to first"),
                                fieldWithPath("page.size").description("links to prev"),
                                fieldWithPath("page.totalElements").description("links to prev"),
                                fieldWithPath("page.totalPages").description("links to prev"),
                                fieldWithPath("page.number").description("links to prev")
                        )
                ));

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