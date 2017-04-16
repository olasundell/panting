package se.atrosys.pant.model;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

/**
 * TODO write documentation
 */
@Entity
public class ContainerPrice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	private Container container;
	private LocalDate validFrom;
	private LocalDate validTo;
	private Double amount;
}
