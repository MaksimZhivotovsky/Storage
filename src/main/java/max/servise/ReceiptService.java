package max.servise;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import max.model.CalculationProduct;
import max.model.Product;
import max.model.ProductOnStorage;
import max.model.Receipt;
import max.model.Storage;
import max.repository.CalculationProductRepository;
import max.repository.ProductOnStorageRepository;
import max.repository.ProductRepository;
import max.repository.ReceiptRepository;
import max.repository.StorageRepository;

@Service
public class ReceiptService {

	private final CalculationProductRepository calculationProductRepository;
	private final ReceiptRepository receiptRepository;
	private final ProductOnStorageRepository productOnStorageRepository;
	private final ProductRepository productRepository;
	private final StorageRepository storageRepository;
	
	public ReceiptService(ReceiptRepository receiptRepository, ProductOnStorageRepository productOnStorageRepository,
			ProductRepository productRepository, CalculationProductRepository calculationProductRepository,
			StorageRepository storageRepository) {
		this.calculationProductRepository = calculationProductRepository;
		this.receiptRepository = receiptRepository;
		this.productOnStorageRepository = productOnStorageRepository;
		this.productRepository = productRepository;
		this.storageRepository = storageRepository;	
	}
	
	@Transactional
	public Receipt addReceipt(Receipt receipt) {
		Product product = productRepository.findById(receipt.getProduct().getId())
				.orElseThrow(() -> new IllegalArgumentException("Product id incorect"));
		
		Storage storage = storageRepository.findById(receipt.getStorageId().getId())
				.orElseThrow(() -> new IllegalArgumentException("Storage id incorrect"));
		receipt.setProduct(product);
		receipt.setStorageId(storage);
		
		CalculationProduct calculationProduct = calculationProductRepository
				.findByProduct_IdAndStorage_Id(receipt.getProduct().getId(), receipt.getStorageId().getId());
        ProductOnStorage productOnStorage = productOnStorageRepository
        		.findByProduct_Id(receipt.getProduct().getId());

        final Receipt result = receiptRepository.save(receipt);
        product.setLastPurchasePrice(result.getPurchasePrice());
        productRepository.save(product);

        if (productOnStorage == null) {
            productOnStorageRepository.save(new ProductOnStorage(receipt.getProduct(), receipt.getQuantity()));
        } else {
            productOnStorageRepository.save(new ProductOnStorage(productOnStorage.getId(), receipt.getProduct(), receipt.getQuantity() + productOnStorageRepository.getOne(productOnStorage.getId()).getQuantity()));
        }

        if (calculationProduct == null) {
            calculationProductRepository.save(new CalculationProduct(receipt.getProduct(), receipt.getQuantity(), receipt.getStorageId()));
        } else {
            calculationProductRepository.save(new CalculationProduct(calculationProduct.getId(), receipt.getProduct(), receipt.getQuantity() + calculationProductRepository.getOne(calculationProduct.getId()).getQuantity(), calculationProduct.getStorage()));
        }

        return result;
    }

    public List<Receipt> getAllReceipts() {
        return receiptRepository.findAll();
    }

    public void deleteById(Long id) {
        receiptRepository.deleteById(id);
    }
	
}
