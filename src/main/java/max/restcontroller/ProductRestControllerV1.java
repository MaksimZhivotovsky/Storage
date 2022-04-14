package max.restcontroller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import max.model.Product;
import max.model.ProductOnStorage;
import max.servise.ProductService;

@RestController
@RequestMapping("/api/v1/products")
public class ProductRestControllerV1 {
	
	private final ProductService productService;
	
	@Autowired
	public ProductRestControllerV1(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping(value = "/on_storages", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<ProductOnStorage> getProductsOnReceipts() {
		return productService.getAllProductsOnStorages();
	}
	
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<Product> getProducts() {
		return productService.getAllProducts();
	}
	
	@PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity addProduct(@Valid @RequestBody Product product, BindingResult bindingResult) {
		final Product result = productService.addProduct(product);
		if(bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body("Введены некорректные данные");
		}
		
		return ResponseEntity.ok().body(result);
	}
	@GetMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity deleteProduct(@PathVariable Long id) {
		productService.deleteById(id);
		return ResponseEntity.ok().body(id);
	}
	
	@PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity changeProduct(@Valid @RequestBody Product product, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body("Введены некорректные данные");
		}
		productService.addProduct(product);
		return ResponseEntity.ok().body(product);
	}
	
	@GetMapping("/{id}")
	public Product showProductById(@PathVariable(value = "id") Long id) {
		return productService.get(id).orElse(new Product());
	}

}
