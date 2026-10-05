package mx.edu.itspa.biblioteca.access;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitReasonRepository extends JpaRepository<VisitReason, Long> {

    List<VisitReason> findByActiveTrueOrderByDisplayOrderAscNameAsc();
}
