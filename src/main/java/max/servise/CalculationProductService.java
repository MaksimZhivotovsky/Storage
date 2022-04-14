package max.servise;

import java.util.List;

import org.springframework.stereotype.Service;

import max.model.CalculationProduct;
import max.repository.CalculationProductRepository;

@Service
public class CalculationProductService {
	
	private final CalculationProductRepository calculationProductRepository;

	public CalculationProductService(CalculationProductRepository calculationProductRepository) {
		this.calculationProductRepository = calculationProductRepository;
	}
	
	public List<CalculationProduct> getAllCalculations() {
		return calculationProductRepository.findAll();
	}
	
	public CalculationProduct addCalculation(CalculationProduct calculation) {
		return calculationProductRepository.save(calculation);
	}
	
	public void deleteById(Long id) {
		calculationProductRepository.deleteById(id);
	}
	
}
