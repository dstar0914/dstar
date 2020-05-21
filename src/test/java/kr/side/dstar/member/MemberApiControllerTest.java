package kr.side.dstar.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.side.dstar.auth.dto.LoginRequestDto;
import kr.side.dstar.member.dto.MemberSaveRequestDto;
import kr.side.dstar.token.dto.TokenResponseDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberService memberService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void save() throws Exception {
        //given
        String name = "tester";
        String password = "pass";
        String phone = "010-1234-4321";
        String email = "tester@email.com";
        MemberStatus status = MemberStatus.AUTHORIZED;
        Set<MemberRole> roles = Collections.singleton(MemberRole.ADMIN);

        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .email(email)
                .name(name)
                .password(password)
                .phone(phone)
                .status(status)
                .roles(roles)
                .build();

        //when, then
        String path = "data.";

        mockMvc.perform(post("/api/member")
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

        MemberSaveRequestDto member = MemberSaveRequestDto.builder()
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
}