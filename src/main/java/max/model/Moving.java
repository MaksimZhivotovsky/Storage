package max.model;

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

@Data
@NoArgsConstructor
@Entity
public class Moving {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long number;
	
	@Min(value = 1)
	private Long quantity;
	
	@ManyToOne
	@JoinColumn(name = "storage_from_id")
	@JsonProperty(value = "storage_from_id")
	private Storage storageForm;
	
	@ManyToOne
	@JoinColumn(name = "storage_to_id")
	@JsonProperty(value = "storage_to_id")
	@NotNull
	private Storage storageTo;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	@JsonProperty(value = "product_id")
	@NotNull
	private Product product;
	
}
