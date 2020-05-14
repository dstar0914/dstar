package kr.side.dstar.member.dto;

import kr.side.dstar.member.Member;
import kr.side.dstar.member.MemberRole;
import kr.side.dstar.member.MemberStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Getter
public class MemberSaveRequestDto {

    private String name;
    private String password;
    private String phone;
    private String email;
    private MemberStatus status;
    private Set<MemberRole> roles;

    @Builder
    public MemberSaveRequestDto(String name, String password, String phone,
                                String email, MemberStatus status, Set<MemberRole> roles) {
        this.name       = name;
        this.password   = password;
        this.phone      = phone;
        this.email      = email;
        this.status     = status;
        this.roles      = roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Member toEntity() {
        return Member.builder()
                .phone(phone)
                .password(password)
                .email(email)
                .name(name)
                .status(status)
                .roles(roles)
                .build();
    }
}
