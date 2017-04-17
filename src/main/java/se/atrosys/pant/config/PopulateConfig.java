package se.atrosys.pant.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import se.atrosys.pant.model.Container;
import se.atrosys.pant.model.ContainerPrice;
import se.atrosys.pant.model.Customer;
import se.atrosys.pant.model.Panting;
import se.atrosys.pant.repository.ContainerRepository;
import se.atrosys.pant.repository.CustomerRepository;
import se.atrosys.pant.repository.InvoiceRepository;
import se.atrosys.pant.repository.PantingRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

/**
 * TODO write documentation
 */
@Configuration
public class PopulateConfig {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final ContainerRepository containerRepository;
	private final CustomerRepository customerRepository;
	private final InvoiceRepository invoiceRepository;
	private final PantingRepository pantingRepository;

	@Autowired
	public PopulateConfig(ContainerRepository containerRepository,
	                      CustomerRepository customerRepository,
	                      InvoiceRepository invoiceRepository,
	                      PantingRepository pantingRepository) {
		this.containerRepository = containerRepository;
		this.customerRepository = customerRepository;
		this.invoiceRepository = invoiceRepository;
		this.pantingRepository = pantingRepository;
	}

	@PostConstruct
	public void populate() {
		logger.info("Populating database");

		customerRepository.deleteAll();
		containerRepository.deleteAll();
		pantingRepository.deleteAll();

		Customer ladlas = customerRepository.save(Customer.builder()
				.address("Hemma")
				.email("foo@bar.it")
				.name("Ladlas Ladlassen")
				.build());

		logger.debug("Customer {}", ladlas);

		Container burk = containerRepository.save(Container.builder()
				.description("33 ml aluminium")
				.price(Arrays.asList(buildPrice(1.0, LocalDate.of(2010, 1, 1), LocalDate.of(2015, 12, 31)),
						buildPrice(2.0, LocalDate.of(2016, 1, 1), LocalDate.of(2020, 12, 31))))
				.build());

		logger.debug("Container {}", burk);

		Panting first = pantingRepository.save(Panting.builder()
				.container(Collections.singletonList(burk))
				.customer(ladlas)
				.madeAt(LocalDate.of(2012, 3, 12))
				.build());

		logger.debug("Panting {}", first);
	}

	private ContainerPrice buildPrice(Double amount, LocalDate from, LocalDate to) {
		return ContainerPrice.builder()
				.amount(amount)
				.validFrom(from)
				.validTo(to)
				.build();
	}
}
