package br.com.dev.jpa_docker.repositories;

import br.com.dev.jpa_docker.models.Student;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, UUID> {

  List<Student> findByFullNameContainingIgnoreCase(String name);

}
