package max.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import max.model.Moving;

public interface MovingRepository extends JpaRepository<Moving, Long>, JpaSpecificationExecutor<Moving> {

}
