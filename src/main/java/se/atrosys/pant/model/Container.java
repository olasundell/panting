package se.atrosys.pant.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

/**
 * TODO write documentation
 */
@Entity
@Builder
@Data
@JsonDeserialize(builder = Container.ContainerBuilder.class)
public class Container {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String description;
//	private List<ContainerPrice> prices;
	@JsonPOJOBuilder(withPrefix = "")
	public static class ContainerBuilder {}
}
