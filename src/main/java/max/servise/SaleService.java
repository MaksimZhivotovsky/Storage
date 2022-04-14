package max.servise;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import max.model.CalculationProduct;
import max.model.Product;
import max.model.ProductOnStorage;
import max.model.Sale;
import max.model.Storage;
import max.repository.CalculationProductRepository;
import max.repository.ProductOnStorageRepository;
import max.repository.ProductRepository;
import max.repository.SaleRepository;
import max.repository.StorageRepository;

@Service
public class SaleService {
	
	private final SaleRepository saleRepository;
	private final ProductRepository productRepository;
	private final StorageRepository storageRepository;
	private final ProductOnStorageRepository productOnStorageRepository;
	private final CalculationProductRepository calculationProductRepository;

	public SaleService(SaleRepository saleRepository, CalculationProductRepository calculationProductRepository,
			StorageRepository storageRepository, ProductOnStorageRepository productOnStorageRepository,
			ProductRepository productRepository) {
		this.saleRepository = saleRepository;
		this.productRepository = productRepository;
		this.storageRepository = storageRepository;
		this.productOnStorageRepository = productOnStorageRepository;
		this.calculationProductRepository = calculationProductRepository;
	}
	
	@Transactional
	public Sale addSale(Sale sale) {
		Product product = productRepository.findById(sale.getProduct().getId())
				.orElseThrow(() -> new IllegalArgumentException("Product id incorrect "));
		
		Storage storage = storageRepository.findById(sale.getStorage().getId())
				.orElseThrow(() -> new IllegalArgumentException("Storage id incorrect"));
		
		sale.setProduct(product);
		sale.setStorage(storage);
		Sale result = null;
		
		CalculationProduct calculationProduct = calculationProductRepository
				.findByProduct_IdAndStorage_Id(sale.getProduct().getId(),
						sale.getStorage().getId());
		
		ProductOnStorage productOnStorage = productOnStorageRepository
				.findByProduct_Id(sale.getProduct().getId());
		
		if(calculationProductRepository.getOne(calculationProduct.getId()).getQuantity() >= sale.getQuantity() 
				&& productOnStorageRepository.getOne(productOnStorage.getId()).getQuantity() >= sale.getQuantity()) {
			result = saleRepository.save(sale);
			
			product.setLastSalePrice(result.getSale_price());
			productRepository.save(product);
			
			if(productOnStorage == null) {
				productOnStorageRepository.save(new ProductOnStorage(sale.getProduct(), sale.getQuantity()));
			} else {
				productOnStorageRepository.save(new ProductOnStorage(productOnStorage.getId(), sale.getProduct(),
						productOnStorageRepository.getOne(productOnStorage.getId()).getQuantity() - sale.getQuantity()));
			}
			
			if(calculationProduct == null) {
				calculationProductRepository.save(new CalculationProduct(sale.getProduct(), sale.getQuantity(), sale.getStorage()));
			} else {
				calculationProductRepository.save(new CalculationProduct(
						calculationProduct.getId(), sale.getProduct(), calculationProductRepository.getOne(calculationProduct.getId()).getQuantity()
						- sale.getQuantity(), calculationProduct.getStorage()));
			}
			
		}
		return result;	
	}
	
	public List<Sale> getAllSales() {
		return saleRepository.findAll();
	}
	
	public void deleteById (Long id) {
		saleRepository.deleteById(id);
	}
	
}
