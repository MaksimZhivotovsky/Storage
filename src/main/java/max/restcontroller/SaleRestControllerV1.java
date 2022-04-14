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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import max.model.Sale;
import max.servise.SaleService;

@RestController
@RequestMapping("/api/v1/sales")
public class SaleRestControllerV1 {
	
	private final SaleService saleService;
	
	public SaleRestControllerV1(SaleService saleService) {
		this.saleService = saleService;
	}
	
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Sale> getSales() {
		return saleService.getAllSales();
	}
	
	@PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity addSale(@Valid @RequestBody Sale sale, BindingResult bindingresult) {
		if(bindingresult.hasErrors()) {
			return ResponseEntity.badRequest().body("Введены некорректные данные");
		}
		final Sale result = saleService.addSale(sale);
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity deleteSale(@PathVariable Long id) {
		saleService.deleteById(id);
		return ResponseEntity.ok().body(id);
	}
	
	@PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity changeSale(@Valid @RequestBody Sale sale, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body("Введены некорректные данные");
		}
		saleService.addSale(sale);
		return ResponseEntity.ok().body(sale);
	}
 
}
