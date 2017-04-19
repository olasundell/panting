package se.atrosys.pant.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.List;

/**
 * TODO write documentation
 */
@Entity
@Builder
@Data
@JsonDeserialize(builder = Container.ContainerBuilder.class)
@NoArgsConstructor
@AllArgsConstructor
public class Container {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String description;
	@OneToMany(mappedBy = "container", cascade = CascadeType.ALL)
	private List<ContainerPrice> price;

	@Transient
	public Double getPriceAt(LocalDate date) {
		for (ContainerPrice p: price) {
			if ((p.getValidTo().isAfter(date) || p.getValidTo().isEqual(date)) &&
					(p.getValidFrom().isBefore(date) || p.getValidFrom().isEqual(date))) {
				return p.getAmount();
			}
		}
		// TODO throw exception
		return 0.0;
	}


	@JsonPOJOBuilder(withPrefix = "")
	public static class ContainerBuilder {}
}
