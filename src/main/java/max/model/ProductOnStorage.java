package max.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class ProductOnStorage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	@NotNull
	private Product product;
	
	@Min(value = 1)
	private Long quantity;
	
	public ProductOnStorage(Product product, Long quantity) {
		this.product = product;
		this.quantity = quantity;
	}
	
	public ProductOnStorage(Long id, Product product, Long quantity) {
		this.product = product;
		this.quantity = quantity;
		this.id = id;
	}
}
