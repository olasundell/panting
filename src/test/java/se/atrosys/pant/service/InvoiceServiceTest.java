package se.atrosys.pant.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import se.atrosys.pant.model.Container;
import se.atrosys.pant.model.ContainerPrice;
import se.atrosys.pant.model.Customer;
import se.atrosys.pant.model.Invoice;
import se.atrosys.pant.model.Panting;
import se.atrosys.pant.repository.ContainerPriceRepository;
import se.atrosys.pant.repository.ContainerRepository;
import se.atrosys.pant.repository.CustomerRepository;
import se.atrosys.pant.repository.InvoiceRepository;
import se.atrosys.pant.repository.PantingRepository;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * TODO write documentation
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class InvoiceServiceTest {
	private final Logger logger  = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ContainerPriceRepository containerPriceRepository;

	@Autowired
	private ContainerRepository containerRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private PantingRepository pantingRepository;

	private InvoiceService invoiceService;

	@Test
	public void shouldCalculateInvoice() throws Exception {
		setup();
		invoiceService = new InvoiceService(invoiceRepository, customerRepository, pantingRepository);
		Invoice result = invoiceService.getInvoice(1, 2012, 3);
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getAmount());
		Assert.assertNotNull(result.getCustomer());
		Assert.assertEquals(new Double(4.0), result.getAmount());
	}

	private void setup() {
		Customer ladlas = Customer.builder()
				.address("Hemma")
				.email("foo@bar.it")
				.name("Ladlas Ladlassen")
				.build();

		ladlas.setId((Integer) this.entityManager.persistAndGetId(ladlas));

		Container burk = Container.builder()
				.description("33 cl aluminium")
				.build();

		burk.setPrice(Arrays.asList(buildPrice(burk, 1.0, LocalDate.of(2010, 1, 1), LocalDate.of(2015, 12, 31)),
				buildPrice(burk, 2.0, LocalDate.of(2016, 1, 1), LocalDate.of(2020, 12, 31))));

		burk.setId((Integer) this.entityManager.persistAndGetId(burk));

		Container flaska = Container.builder()
				.description("50 cl plast")
				.build();

		flaska.setPrice(Arrays.asList(buildPrice(flaska, 2.0, LocalDate.of(2010, 1, 1), LocalDate.of(2015, 12, 31)),
				buildPrice(flaska, 3.0, LocalDate.of(2016, 1, 1), LocalDate.of(2020, 12, 31))));

		flaska.setId((Integer) this.entityManager.persistAndGetId(flaska));

		Panting first = Panting.builder()
				.container(Arrays.asList(burk, burk, flaska))
				.customer(ladlas)
				.madeAt(LocalDate.of(2012, 3, 12))
				.build();

		first.setId((Integer) this.entityManager.persistAndGetId(first));

		Panting second = Panting.builder()
				.container(Arrays.asList(burk, burk, flaska))
				.customer(ladlas)
				.madeAt(LocalDate.of(2012, 4, 1))
				.build();

		second.setId((Integer) this.entityManager.persistAndGetId(second));
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