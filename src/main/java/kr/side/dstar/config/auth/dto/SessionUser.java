package kr.side.dstar.config.auth.dto;

import kr.side.dstar.domain.member.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;

    public SessionUser(Member member) {
        this.name   = member.getName();
        this.email  = member.getEmail();
    }
}
