package kr.side.dstar.member.dto;

import kr.side.dstar.member.Member;
import kr.side.dstar.member.MemberRole;
import kr.side.dstar.member.MemberStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
public class MemberResponseDto {

    private Long id;
    private String name;
    private String password;
    private String phone;
    private String email;
    private MemberStatus status;
    private Set<MemberRole> roles;

    @Builder
    public MemberResponseDto(Member member) {
        this.id         = member.getId();
        this.name       = member.getName();
        this.password   = member.getPassword();
        this.phone      = member.getPhone();
        this.email      = member.getEmail();
        this.status     = member.getStatus();
        this.roles      = member.getRoles();
    }
}
