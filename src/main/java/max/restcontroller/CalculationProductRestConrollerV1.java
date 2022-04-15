package max.restcontroller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import max.model.CalculationProduct;
import max.servise.CalculationProductService;

@RestController
@RequestMapping("/api/v1/calculations")
public class CalculationProductRestConrollerV1 {

	private final CalculationProductService calculationProductService;
	
	public CalculationProductRestConrollerV1(CalculationProductService calculationProductService) {
		this.calculationProductService = calculationProductService;
	}
	
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<CalculationProduct> getCalculations() {
		return calculationProductService.getAllCalculations();
	}
	
	@PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addCalculation(@Valid @RequestBody CalculationProduct calculation, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body("Введены некорректные данные");
		}
		final CalculationProduct result = calculationProductService.addCalculation(calculation);
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> deleteCalculation(@PathVariable Long id) {
		calculationProductService.deleteById(id);
		return ResponseEntity.ok().body(id);
	}
	
	@PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> changeCalculation(@Valid @RequestBody CalculationProduct calculationProduct, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body("Введены некорректные данные");
		}
		final CalculationProduct result = calculationProductService.addCalculation(calculationProduct);
		return ResponseEntity.ok().body(result);
	}
	
}
