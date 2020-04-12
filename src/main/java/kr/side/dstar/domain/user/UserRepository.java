package kr.side.dstar.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 소셜 로그인 반환값 중 이메일을 통해 기존 생성 여부 판단하는 메소드
    Optional<User> findByEmail(String email);
}
