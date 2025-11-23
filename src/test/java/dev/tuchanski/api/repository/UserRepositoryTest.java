package dev.tuchanski.api.repository;

import static org.assertj.core.api.Assertions.assertThat;
import dev.tuchanski.api.dto.user.UserRequestDTO;
import dev.tuchanski.api.entity.User;
import dev.tuchanski.api.entity.enums.UserRole;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get user successfully from DB")
    void findUserByUsernameSuccess() {
        UserRequestDTO dto = new UserRequestDTO(
                "Guilherme Rocha", "rocha", "rocha@gmail.com", "rocha"
        );
        createUser(dto);

        User result = (User) userRepository.findByUsername(dto.username());

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("rocha");
    }

    @Test
    @DisplayName("Should not get user successfully from DB when user not exists")
    void findUserByUsernameFailure() {

        User result = (User) userRepository.findByUsername("jojo");

        assertThat(result).isNull();
    }

    private void createUser(UserRequestDTO data) {
        User user = new User();
        user.setName(data.name());
        user.setUsername(data.username());
        user.setEmail(data.email());
        user.setPassword(data.password());
        user.setRole(UserRole.USER);
        userRepository.save(user);
        entityManager.persist(user);
    }
}