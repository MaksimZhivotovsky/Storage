package max.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Receipt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long number;
	
	@Min(value = 1)
	private Long quantity;
	
	@NotNull
	private BigDecimal purchasePrice;
	
	@ManyToOne
	@JoinColumn(name = "storage_id")
	@JsonProperty(value = "storage_id")
	@NotNull
	private Storage storageId;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name = "product_id")
	@JsonProperty(value = "product_id")
	@NotNull
	private Product product;
	
}
