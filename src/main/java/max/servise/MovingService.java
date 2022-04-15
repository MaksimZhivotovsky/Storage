package max.servise;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import max.model.CalculationProduct;
import max.model.Moving;
import max.model.Product;
import max.model.Storage;
import max.repository.CalculationProductRepository;
import max.repository.MovingRepository;
import max.repository.ProductRepository;
import max.repository.StorageRepository;

@Service
public class MovingService {
    
    private final MovingRepository movingRepository;
    private final ReceiptService receiptService;
    private final SaleService saleService;
    private final ProductRepository productRepository;
    private final StorageRepository storageRepository;
    private final CalculationProductRepository calculationProductRepository;
    
    public MovingService(MovingRepository movingRepository, ProductRepository productRepository,
    		SaleService saleService, StorageRepository storageRepository,
    		CalculationProductRepository calculationProductRepository, ReceiptService receiptService) {
		this.movingRepository = movingRepository;
		this.receiptService = receiptService;
		this.saleService = saleService;
		this.productRepository = productRepository;
		this.storageRepository = storageRepository;
		this.calculationProductRepository = calculationProductRepository;
    	
    }

    @Transactional
    public Moving addMoving(Moving moving){
        Product product = productRepository.findById(moving.getProduct().getId())
                .orElseThrow(() -> new IllegalArgumentException("Product id incorrect"));

        Storage storageFrom = storageRepository.findById(moving.getStorageForm().getId())
                .orElseThrow(() -> new IllegalArgumentException("Storage from id incorrect"));

        Storage storageTo = storageRepository.findById(moving.getStorageTo().getId())
                .orElseThrow(() -> new IllegalArgumentException("Storage from id incorrect"));


        moving.setProduct(product);
        moving.setStorageForm(storageFrom);
        moving.setStorageTo(storageTo);
        Moving result = null;

        CalculationProduct calculationProductFrom = calculationProductRepository
        		.findByProduct_IdAndStorage_Id(moving.getProduct().getId(), moving.getStorageForm().getId());
        CalculationProduct calculationProductTo = calculationProductRepository
        		.findByProduct_IdAndStorage_Id(moving.getProduct().getId(), moving.getStorageTo().getId());


        if (calculationProductRepository.getOne(calculationProductFrom.getId()).getQuantity() >=
        moving.getQuantity() ){
            result = movingRepository.save(moving);
            calculationProductRepository.save(new CalculationProduct(calculationProductFrom.getId(), moving.getProduct(), 
            		calculationProductRepository.getOne(calculationProductFrom.getId()).getQuantity() - moving.getQuantity(),
            		moving.getStorageForm()));
            if (calculationProductTo == null) {
                calculationProductRepository.save(new CalculationProduct(moving.getProduct(), 
                		moving.getQuantity(), moving.getStorageTo()));
            } else {

                calculationProductRepository.save(new CalculationProduct(calculationProductTo.getId(),moving.getProduct(),
                		calculationProductRepository.getOne(calculationProductTo.getId()).getQuantity() 
                		+ moving.getQuantity(), moving.getStorageTo()));
            }


        }

        return result;
    }

    public List<Moving> getAllMovings() {
        return movingRepository.findAll();
    }

    public void deleteById(Long id) {
        movingRepository.deleteById(id);
    }
}
