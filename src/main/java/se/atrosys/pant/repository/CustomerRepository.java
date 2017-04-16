package se.atrosys.pant.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import se.atrosys.pant.model.Customer;

/**
 * TODO write documentation
 */
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer> {
}
