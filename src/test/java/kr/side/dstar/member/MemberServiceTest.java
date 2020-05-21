package kr.side.dstar.member;

import kr.side.dstar.member.dto.MemberResponseDto;
import kr.side.dstar.member.dto.MemberSaveRequestDto;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @After
    public void cleanAll() throws Exception {
        memberRepository.deleteAll();
    }

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

        //when
        requestDto.setPassword(passwordEncoder.encode(password));
        MemberResponseDto responseDto = memberService.saveMember(requestDto);

        //then
        assertThat(responseDto.getEmail().equals(email));
    }

    @Test
    public void findByUserName() throws Exception {
        //given
        String password     = "pass";
        String name         = "email@email.com";

        MemberSaveRequestDto member = MemberSaveRequestDto.builder()
                .email(name)
                .password(password)
                .status(MemberStatus.AUTHORIZED)
                .roles(Stream.of(MemberRole.ADMIN).collect(Collectors.toSet()))
                .build();

        memberService.saveMember(member);

        //when
        UserDetailsService userDetailsService = memberService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(name);

        //then
        assertThat(passwordEncoder.matches(password, userDetails.getPassword())).isTrue();
    }

    @Test
    public void findByUserNameFail() {
        //expected
        String username = "1234556@test.com";

        expectedException.expect(UsernameNotFoundException.class);
        expectedException.expectMessage(Matchers.containsString(username));

        //when
        memberService.loadUserByUsername(username);

//        try {
//            memberService.loadUserByUsername(username);
//            fail("faild");
//        }
//        catch (UsernameNotFoundException e) {
//            assertThat(e.getMessage()).containsSequence(username);
//
//        }
    }
}