package kr.side.dstar.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 소셜 로그인 반환값 중 이메일을 통해 기존 생성 여부 판단하는 메소드
    Optional<Member> findByEmail(String name);
}
