package max.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import max.model.CalculationProduct;

public interface CalculationProductRepository extends JpaRepository<CalculationProduct, Long>, JpaSpecificationExecutor<CalculationProduct> {

	CalculationProduct findByProduct_IdAndStorage_Id(Long productId, Long storageId);
	
}
