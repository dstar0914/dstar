package kr.side.dstar.configs;

import kr.side.dstar.domain.member.Member;
import kr.side.dstar.domain.member.MemberService;
import kr.side.dstar.domain.member.MemberStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {
            @Autowired
            MemberService memberService;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                Member member = Member.builder()
                        .email("test@email.com")
                        .password("pass")
                        .status(Stream.of(MemberStatus.AUTHORIZED).collect(Collectors.toSet()))
                        .build();

                memberService.saveMember(member);
            }
        };
    }
}
