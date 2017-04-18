package se.atrosys.pant.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import se.atrosys.pant.model.Customer;
import se.atrosys.pant.model.Panting;

import java.util.List;

/**
 * TODO write documentation
 */
public interface PantingRepository extends PagingAndSortingRepository<Panting, Integer> {
	List<Panting> findByCustomer(Customer customer);
	List<Panting> findByCustomerId(Integer customerId);
}
