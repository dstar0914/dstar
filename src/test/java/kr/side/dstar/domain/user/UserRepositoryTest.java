package kr.side.dstar.domain.user;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @After
    public void clearAll() {
        userRepository.deleteAll();
    }

    @Test
    public void save() {
        String name     = "user1";
        String status   = "AUTHORIZED";
        String phone    = "010-1234-1234";
        String email    = "user1@email.com";

        userRepository.save(User.builder()
                .name(name)
                .status(status)
                .phone(phone)
                .email(email)
                .build());

        List<User> userList = userRepository.findAll();

        assertThat(userList.get(0).getName()).isEqualTo(name);
        assertThat(userList.get(0).getStatus()).isEqualTo(status);
        assertThat(userList.get(0).getPhone()).isEqualTo(phone);
        assertThat(userList.get(0).getEmail()).isEqualTo(email);
    }
}