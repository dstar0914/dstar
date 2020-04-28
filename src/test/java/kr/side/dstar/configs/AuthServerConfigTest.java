package kr.side.dstar.configs;

import kr.side.dstar.domain.member.Member;
import kr.side.dstar.domain.member.MemberService;
import kr.side.dstar.domain.member.MemberStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServerConfigTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getAuthToken() throws Exception {
        //given
        String username = "abc@email.com";
        String password = "pass";

        Member member = Member.builder()
                .email(username)
                .password(password)
                .status(Stream.of(MemberStatus.AUTHORIZED).collect(Collectors.toSet()))
                .build();

        this.memberService.saveMember(member);

        String clientId     = "myApp";
        String clientSecret = "pass";

        mockMvc.perform(post("/oauth/token")
                .with(httpBasic(clientId, clientSecret))
                .param("username", username)
                .param("password", password)
                .param("grant_type", "password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());
    }
}