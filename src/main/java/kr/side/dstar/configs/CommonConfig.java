package kr.side.dstar.configs;

import kr.side.dstar.member.MemberRole;
import kr.side.dstar.member.MemberService;
import kr.side.dstar.member.MemberStatus;
import kr.side.dstar.member.dto.MemberSaveRequestDto;
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
public class CommonConfig {

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
                MemberSaveRequestDto member = MemberSaveRequestDto.builder()
                        .email("test@email.com")
                        .password("pass")
                        .status(MemberStatus.AUTHORIZED)
                        .roles(Stream.of(MemberRole.ADMIN).collect(Collectors.toSet()))
                        .build();

                memberService.saveMember(member);
            }
        };
    }
}
