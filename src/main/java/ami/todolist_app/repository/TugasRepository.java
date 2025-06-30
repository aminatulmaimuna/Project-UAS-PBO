package ami.todolist_app.repository;

import ami.todolist_app.model.Tugas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TugasRepository extends JpaRepository<Tugas, Long> {
}

