package se.atrosys.pant.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import se.atrosys.pant.model.Customer;
import se.atrosys.pant.repository.ContainerPriceRepository;
import se.atrosys.pant.repository.ContainerRepository;
import se.atrosys.pant.repository.CustomerRepository;

import static org.junit.Assert.*;

/**
 * TODO write documentation
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class InvoiceServiceTest {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ContainerPriceRepository containerPriceRepository;

	@Autowired
	private ContainerRepository containerRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	public void testExample() throws Exception {
		this.entityManager.persist(Customer.builder().build());
//		this.entityManager.persist(new User("sboot", "1234"));
//		User user = this.repository.findByUsername("sboot");
//		assertThat(user.getUsername()).isEqualTo("sboot");
//		assertThat(user.getVin()).isEqualTo("1234");
	}

}