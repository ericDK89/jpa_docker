package br.com.dev.jpa_docker.repositories;

import br.com.dev.jpa_docker.models.House;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, UUID> {

  Optional<House> findByHouse(String house);

}
