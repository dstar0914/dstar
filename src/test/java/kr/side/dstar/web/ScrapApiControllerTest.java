package kr.side.dstar.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.side.dstar.auth.dto.LoginRequestDto;
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
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class ScrapApiControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ScrapRepository scrapRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

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
        String path = "data.";

        mockMvc.perform(post("/api/scrap")
                .header("X-AUTH-TOKEN", getJwtToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(path+"id").exists());
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

        return token.getAccessToken();
    }

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
        String path = "data.";

        mockMvc.perform(put("/api/scrap/{id}",updateId)
                .header("X-AUTH-TOKEN", getJwtToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(path+"id").exists())
                .andExpect(jsonPath(path+"url").value(expectedUrl))
                .andExpect(jsonPath(path+"data").value(expectedData));
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
        String path = "data.";

        mockMvc.perform(get("/api/scrap/{id}", savedId)
                .header("X-AUTH-TOKEN", getJwtToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(path+"id").exists())
                .andExpect(jsonPath(path+"url").value(url))
                .andExpect(jsonPath(path+"data").value(data));
    }

    @Test
    public void delete() throws Exception {
        //given
        String url  = "delete_url";
        String data = "delete_data";

        Scrap savedScrap = scrapRepository.save(Scrap.builder()
                .url(url)
                .data(data)
                .build());

        Long deleteId = savedScrap.getId();

        //then
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/scrap/{id}", deleteId)
                .header("X-AUTH-TOKEN", getJwtToken()))
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
        String path = "list[0].";

        this.mockMvc.perform(get("/api/scrap")
                .header("X-AUTH-TOKEN", getJwtToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(path+"id").exists())
                .andExpect(jsonPath(path+"url").exists());
    }

    private void createScrap(int i) {
        Scrap scrap = Scrap.builder()
                .url("url"+i)
                .data("data"+i)
                .build();

        scrapRepository.save(scrap);
    }
}