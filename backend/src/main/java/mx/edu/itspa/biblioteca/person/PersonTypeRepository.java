package mx.edu.itspa.biblioteca.person;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonTypeRepository extends JpaRepository<PersonType, Long> {

    List<PersonType> findByActiveTrueOrderByNameAsc();

    Optional<PersonType> findByCodeAndActiveTrue(String code);
}
