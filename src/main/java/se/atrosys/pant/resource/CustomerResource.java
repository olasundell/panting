package se.atrosys.pant.resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import se.atrosys.pant.model.Customer;
import se.atrosys.pant.repository.CustomerRepository;

import java.util.List;

/**
 * TODO write documentation
 */
@RestController
public class CustomerResource {
	private final CustomerRepository customerRepository;

	public CustomerResource(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@GetMapping(path = "/customers")
	public Page<Customer> getCustomers(Pageable pageable) {
		return customerRepository.findAll(pageable);
	}

	@GetMapping(path = "/customer/{id}")
	public Customer getCustomer(@PathVariable Integer id) {
		return customerRepository.findOne(id);
	}
 }
