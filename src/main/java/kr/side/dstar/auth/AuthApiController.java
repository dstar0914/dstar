package kr.side.dstar.auth;

import kr.side.dstar.auth.dto.LoginRequestDto;
import kr.side.dstar.member.Member;
import kr.side.dstar.member.MemberRepository;
import kr.side.dstar.member.MemberService;
import kr.side.dstar.token.JwtTokenProvider;
import kr.side.dstar.token.dto.TokenRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthApiController {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 첫 인증
    @PostMapping("/auth/login")
    public ResponseEntity login(@RequestBody LoginRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("회원정보가 없습니다."));

        if( !passwordEncoder.matches(requestDto.getPassword(), member.getPassword()) ) {
            throw new IllegalArgumentException("비밀번호가 일치하지않습니다.");
        }

        return ResponseEntity.ok(InitToken(requestDto.getEmail()));
    }

    // access_token 재설정
    @PostMapping("/auth/token")
    public ResponseEntity getToken(@RequestBody TokenRequestDto requestDto) {

        // access_token과 refresh_token을 같이 주면 만료된 상태이니 새로운 access_token 발급
        if( requestDto.getAccessToken()!=null && requestDto.getRefreshToken()!=null ) {
            // 토큰에서 유저정보 추출
            String email = jwtTokenProvider.getUserEmail(requestDto.getRefreshToken());

            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("회원정보가 없습니다."));

            // refresh token db 체크 -> 클라측에 없다는 메세지 보내줘야될것같음
            if( member.getRefreshToken() == requestDto.getRefreshToken() ) {
                throw new IllegalArgumentException("refresh token not valid");
            }

            // refresh token 유효한지 체크 -> 클라측에 없다는 메세지 보내줘야될것같음
            Date now = new Date();
            Date refreshExpire = jwtTokenProvider.getExpiration(requestDto.getRefreshToken());

            if( now.getTime() > refreshExpire.getTime() ) {
                throw new IllegalArgumentException("refresh token not expire");
            }

            // access_token 발행
            return ResponseEntity.ok(createToken(email));
        }
        else {
            throw new IllegalArgumentException("not token");
        }
    }

    // 토큰 생성 메소드
    public Map<String, Object> InitToken(String email) {
        UserDetails user = memberService.loadUserByUsername(email);

        String accessToken = jwtTokenProvider.createToken(user.getUsername(), user.getAuthorities());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUsername(), user.getAuthorities());

        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);
        response.put("expire", jwtTokenProvider.getExpiration(accessToken).getTime()/1000);

        // refresh_token 회원 DB에 업데이트
        memberService.updateRefreshToken(user.getUsername(),refreshToken);

        return response;
    }

    public Map<String, Object> createToken(String email) {
        UserDetails user = memberService.loadUserByUsername(email);

        String accessToken = jwtTokenProvider.createToken(user.getUsername(), user.getAuthorities());

        Map<String, Object> response = new HashMap<>();
        response.put("access_token", accessToken);
        response.put("expire", jwtTokenProvider.getExpiration(accessToken).getTime()/1000);

        return response;
    }
}
