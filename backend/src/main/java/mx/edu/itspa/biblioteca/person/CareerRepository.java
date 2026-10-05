package mx.edu.itspa.biblioteca.person;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerRepository extends JpaRepository<Career, Long> {

    List<Career> findByActiveTrueOrderByNameAsc();
}
