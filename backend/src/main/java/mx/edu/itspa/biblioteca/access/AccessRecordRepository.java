package mx.edu.itspa.biblioteca.access;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessRecordRepository extends JpaRepository<AccessRecord, Long> {

    boolean existsByPersonIdAndCheckedOutAtIsNull(Long personId);

    Optional<AccessRecord> findByIdAndCheckedOutAtIsNull(Long id);

    List<AccessRecord> findByCheckedOutAtIsNullOrderByCheckedInAtDesc();

    List<AccessRecord> findTop100ByOrderByCheckedInAtDesc();
}
