package mx.edu.itspa.biblioteca.auth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {

    Optional<SystemUser> findByUsernameIgnoreCase(String username);

    Optional<SystemUser> findByEmailIgnoreCase(String email);

    boolean existsByUsernameIgnoreCase(String username);
}
