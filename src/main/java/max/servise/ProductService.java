package max.servise;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import max.model.Product;
import max.model.ProductOnStorage;
import max.repository.ProductOnStorageRepository;
import max.repository.ProductRepository;

@Service
public class ProductService {
	
	private final ProductRepository productRepository;
	private final ProductOnStorageRepository productOnStorageRepository;
	
	@Autowired
	public ProductService(ProductRepository productRepository,
			ProductOnStorageRepository productOnStorageRepository) {
		this.productRepository = productRepository;
		this.productOnStorageRepository = productOnStorageRepository;
	}
	
	public void addProductOnStorage(ProductOnStorage productOnStorage) {
		productOnStorageRepository.save(productOnStorage);
	}
	
	public List<ProductOnStorage> getAllProductsOnStorages() {
		return productOnStorageRepository.findAll();
	}
	
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	public Product addProduct(Product product) {
		return productRepository.save(product);
	}
	
	public void deleteById(Long id) {
		productRepository.deleteById(id);
	}
	
	public Optional<Product> get(Long id) {
		return productRepository.findById(id);
	}

}
