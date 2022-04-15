package max.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long quantity;
	
	@NotNull
	private BigDecimal sale_price;
	
	@ManyToOne
	@JoinColumn(name = "storage_id")
	@JsonProperty(value = " storage_id")
	@NotNull
	private Storage storage;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	@JsonProperty(value = "product_id")
	private Product product;
	
}
