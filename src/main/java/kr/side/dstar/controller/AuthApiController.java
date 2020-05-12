//package kr.side.dstar.web;
//
//import kr.side.dstar.configs.JwtTokenProvider;
//import kr.side.dstar.domain.member.MemberService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.hateoas.MediaTypes;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//import java.util.Map;
//
//@Slf4j
//@RequiredArgsConstructor
//@RestController
//@RequestMapping(value = "/login/signin", produces = MediaTypes.HAL_JSON_VALUE)
//public class AuthController {
//
//    private final AuthenticationManager authenticationManager;
//    private final JwtTokenProvider jwtTokenProvider;
//    private final MemberService memberService;
//
//    @PostMapping
//    public ResponseEntity authentication(@Valid @RequestBody Map<String, String> requestBody) {
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(requestBody.get("username"), requestBody.get("password"))
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        UserDetails user = memberService.loadUserByUsername(requestBody.get("username"));
//        String jwt = jwtTokenProvider.createToken(user.getUsername(), user.getAuthorities());
//
//        return ResponseEntity.ok(jwt);
//    }
//
//}
