package max.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import max.model.ProductOnStorage;

public interface ProductOnStorageRepository extends JpaRepository<ProductOnStorage, Long>,JpaSpecificationExecutor<ProductOnStorage> {
	
	ProductOnStorage findByProduct_Id(Long productId);

}
