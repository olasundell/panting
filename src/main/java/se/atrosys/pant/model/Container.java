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
	@JsonPOJOBuilder(withPrefix = "")
	public static class ContainerBuilder {}
}
