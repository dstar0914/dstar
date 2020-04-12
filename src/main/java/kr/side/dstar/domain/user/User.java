package kr.side.dstar.domain.user;

import kr.side.dstar.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private String phone;

    private String email;

    @Builder
    public User(String name, UserStatus status, String phone, String email) {
        this.name   = name;
        this.status = status;
        this.phone  = phone;
        this.email  = email;
    }
}
