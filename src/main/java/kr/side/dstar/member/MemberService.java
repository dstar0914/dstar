package kr.side.dstar.member;

import kr.side.dstar.member.dto.MemberResponseDto;
import kr.side.dstar.member.dto.MemberSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResponseDto saveMember(MemberSaveRequestDto requestDto) {
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        return new MemberResponseDto(memberRepository.save(requestDto.toEntity()));
    }

    @Transactional
    public Member updateRefreshToken(String email, String refreshToken) {
        Member updateMember = memberRepository.findByEmail(email)
                .orElseThrow( () -> new IllegalArgumentException("해당 회원이 존재하지 않습니다.") );

        return updateMember.updateRefreshToken(refreshToken);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return new User(member.getEmail(), member.getPassword(), authorities(member.getRoles()));
    }

    private Set<SimpleGrantedAuthority> authorities(Set<MemberRole> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_"+r.name())).collect(Collectors.toSet());
    }
}
