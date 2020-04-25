package kr.side.dstar.domain.member;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Test
    public void findByUserName() throws Exception {
        //given
        String password     = "pass";
        String email        = "email@email.com";

        Member member = Member.builder()
                .email(email)
                .password(password)
                .status(Stream.of(MemberStatus.AUTHORIZED).collect(Collectors.toSet()))
                .build();

        Member result = this.memberRepository.save(member);

        //when
        UserDetailsService userDetailsService = memberService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        //then
        assertThat(userDetails.getPassword()).isEqualTo(password);
    }
}