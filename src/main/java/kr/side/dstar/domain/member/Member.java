package kr.side.dstar.domain.member;

import kr.side.dstar.domain.BaseTimeEntity;
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

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<MemberStatus> status;

    private String phone;

    @Column(unique = true)
    private String email;

    @Builder
    public Member(String name, Set<MemberStatus> status, String phone, String email, String password) {
        this.name       = name;
        this.status     = status;
        this.phone      = phone;
        this.email      = email;
        this.password   = password;
    }

    public Member update(String name, String phone) {
        this.name   = name;
        this.phone  = phone;

        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
