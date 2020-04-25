package kr.side.dstar.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));

        return new User(member.getEmail(), member.getPassword(), authorities(member.getStatus()));
    }

    private Collection<? extends GrantedAuthority> authorities(Set<MemberStatus> status) {
        return status.stream().map(r -> new SimpleGrantedAuthority("ROLE_"+r.name())).collect(Collectors.toSet());
    }
}
