package se.atrosys.pant.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.YearMonth;

/**
 * TODO write documentation
 */
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch = FetchType.EAGER)
	private Customer customer;
	private LocalDate issuedAt;
	private YearMonth period;
	private Double amount;
	@Transient
	private Boolean definitive;
}
