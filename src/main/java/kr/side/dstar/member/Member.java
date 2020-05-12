package kr.side.dstar.member;

import kr.side.dstar.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<MemberRole> roles;

    private String phone;

    @Column(unique = true)
    private String email;

    private String refreshToken;

    private String expiredAt;

    @Builder
    public Member(String name, MemberStatus status, String phone, String email, String password, Set<MemberRole> roles) {
        this.name       = name;
        this.status     = status;
        this.phone      = phone;
        this.email      = email;
        this.password   = password;
        this.roles      = roles;
    }

    public Member update(String name, String phone) {
        this.name   = name;
        this.phone  = phone;

        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Member updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;

        return this;
    }
}
