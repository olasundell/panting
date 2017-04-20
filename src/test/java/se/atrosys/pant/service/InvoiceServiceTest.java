package se.atrosys.pant.service;

import org.junit.Assert;
import org.junit.Before;
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
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Optional;

/**
 * TODO write documentation
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class InvoiceServiceTest {
	private final Logger logger  = LoggerFactory.getLogger(this.getClass());
	private boolean setupAlready = false;

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
	private Customer customer;
	private Invoice invoice;

	@Test
	public void shouldCalculateStatement() throws Exception {
		Invoice result = invoiceService.getStatement(customer.getId(), 2012, 3);
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getAmount());
		Assert.assertNotNull(result.getCustomer());
		Assert.assertEquals(new Double(4.0), result.getAmount());
		Assert.assertEquals(LocalDate.now(), result.getIssuedAt());
		Assert.assertEquals(YearMonth.of(2012, 3), result.getPeriod());
	}

	@Test
	public void shouldGetInvoice() {
		Optional<Invoice> invoiceOptional = invoiceService.getInvoice(customer.getId(), 2012, 2);
		Assert.assertTrue(invoiceOptional.isPresent());
		Invoice result = invoiceOptional.get();

		Assert.assertTrue(result.getDefinitive());
		Assert.assertEquals(invoice.getAmount(), result.getAmount());
	}

	@Test
	public void shouldCreateInvoice() {
		Invoice result = invoiceService.createInvoiceForPeriod(customer.getId(), 2012, 3);
		Assert.assertTrue(result.getDefinitive());
		Optional<Invoice> invoiceOptional = invoiceService.getInvoice(customer.getId(), 2012, 3);
		Assert.assertTrue(invoiceOptional.isPresent());
	}

	@Before
	public void setup() {
		if (setupAlready) {
			return;
		}
		customer = Customer.builder()
				.address("Hemma")
				.email("foo@bar.it")
				.name("Ladlas Ladlassen")
				.build();

		customer.setId((Integer) this.entityManager.persistAndGetId(customer));

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
				.customer(customer)
				.madeAt(LocalDate.of(2012, 3, 12))
				.build();

		first.setId((Integer) this.entityManager.persistAndGetId(first));

		Panting second = Panting.builder()
				.container(Arrays.asList(burk, burk, flaska))
				.customer(customer)
				.madeAt(LocalDate.of(2012, 4, 1))
				.build();

		second.setId((Integer) this.entityManager.persistAndGetId(second));

		invoice = Invoice.builder()
				.amount(9.0)
				.customer(customer)
				.issuedAt(LocalDate.of(2012, 3, 5))
				.period(YearMonth.of(2012, 2))
				.build();

		invoice.setId((Integer) this.entityManager.persistAndGetId(invoice));

		invoiceService = new InvoiceService(invoiceRepository, customerRepository, pantingRepository);

		setupAlready = true;
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