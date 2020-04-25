package kr.side.dstar.domain.member;

import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @After
    public void clearAll() {
        memberRepository.deleteAll();
    }

//    @Test
//    public void save() {
//        String name         = "user1";
//        MemberStatus status = MemberStatus.AUTHORIZED;
//        String phone        = "010-1234-1234";
//        String email        = "user1@email.com";
//
//        memberRepository.save(Member.builder()
//                .name(name)
//                .status(status)
//                .phone(phone)
//                .email(email)
//                .build());
//
//        List<Member> memberList = memberRepository.findAll();
//
//        assertThat(memberList.get(0).getName()).isEqualTo(name);
//        assertThat(memberList.get(0).getStatus()).isEqualTo(status);
//        assertThat(memberList.get(0).getPhone()).isEqualTo(phone);
//        assertThat(memberList.get(0).getEmail()).isEqualTo(email);
//    }
}