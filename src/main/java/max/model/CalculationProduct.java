package max.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class CalculationProduct {

	@Id
	@GeneratedValue
	private Long id; 
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="product_id")
	@NotNull
	private Product product;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="storage_id")
	@NotNull
	private Storage storage;
	
	@Min(value = 1)
	private Long quantity;
	
	public CalculationProduct(Product product, Long quantity, Storage storage) {
		this.product = product;
		this.quantity = quantity;
		this.storage = storage;
	}
	
	public CalculationProduct(Long id, Product product, Long quantity, Storage storage) {
		this.id = id;
		this.product = product;
		this.storage = storage;
		this.quantity = quantity;
	}
	
}
