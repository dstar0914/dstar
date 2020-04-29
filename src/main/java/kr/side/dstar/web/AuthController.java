package kr.side.dstar.web;

import kr.side.dstar.configs.JwtTokenProvider;
import kr.side.dstar.domain.member.Member;
import kr.side.dstar.domain.member.MemberStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/login/signin", produces = MediaTypes.HAL_JSON_VALUE)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity authentication(@Valid @RequestBody Member member) {
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        member.getEmail(),
//                        member.getPassword()
//                )
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        Set<MemberStatus> status = member.getStatus();

        String jwt = jwtTokenProvider.createToken(member.getEmail(), status);

        return ResponseEntity.ok(jwt);

    }

}
