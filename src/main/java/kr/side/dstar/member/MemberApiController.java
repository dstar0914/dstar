package kr.side.dstar.member;

import kr.side.dstar.member.dto.MemberSaveRequestDto;
import kr.side.dstar.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/member")
public class MemberApiController {

    private final ResponseService responseService;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity save(@RequestBody MemberSaveRequestDto requestDto) {
        return ResponseEntity.ok(responseService.getSingleResult(memberService.saveMember(requestDto)));
    }
}
