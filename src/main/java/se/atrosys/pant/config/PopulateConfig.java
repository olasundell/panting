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
				.description("33 cl aluminium")
//				.price(Arrays.asList(buildPrice(1.0, LocalDate.of(2010, 1, 1), LocalDate.of(2015, 12, 31)),
//						buildPrice(2.0, LocalDate.of(2016, 1, 1), LocalDate.of(2020, 12, 31))))
				.build());
		burk.setPrice(Arrays.asList(buildPrice(burk, 1.0, LocalDate.of(2010, 1, 1), LocalDate.of(2015, 12, 31)),
						buildPrice(burk, 2.0, LocalDate.of(2016, 1, 1), LocalDate.of(2020, 12, 31))));

		containerRepository.save(burk);

		logger.debug("Container {}", burk);

		Container flaska = containerRepository.save(Container.builder()
				.description("50 cl plast")
				.build());

		flaska.setPrice(Arrays.asList(buildPrice(flaska, 2.0, LocalDate.of(2010, 1, 1), LocalDate.of(2015, 12, 31)),
				buildPrice(flaska, 3.0, LocalDate.of(2016, 1, 1), LocalDate.of(2020, 12, 31))));

		containerRepository.save(flaska);

		logger.debug("Container {}", flaska);

		Panting first = pantingRepository.save(Panting.builder()
				.container(Arrays.asList(burk, burk, flaska))
				.customer(ladlas)
				.madeAt(LocalDate.of(2012, 3, 12))
				.build());

		logger.debug("Panting {}", first);
	}

	private ContainerPrice buildPrice(Container container, Double amount, LocalDate from, LocalDate to) {
		return ContainerPrice.builder()
				.container(container)
				.amount(amount)
				.validFrom(from)
				.validTo(to)
				.build();
	}
}
