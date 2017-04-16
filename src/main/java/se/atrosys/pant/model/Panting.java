package se.atrosys.pant.model;

import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

/**
 * TODO write documentation
 */
@Builder
@Entity
public class Panting {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	private Customer customer;
	@OneToMany
	private List<Container> container;
	private LocalDate madeAt;
}
