package se.atrosys.pant.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import se.atrosys.pant.model.Customer;
import se.atrosys.pant.model.Invoice;

import java.time.YearMonth;
import java.util.List;

/**
 * TODO write documentation
 */
public interface InvoiceRepository extends PagingAndSortingRepository<Invoice, Integer> {
	Invoice findByCustomerIdAndPeriod(Integer customerId, YearMonth yearMonth);
	List<Invoice> findAllByCustomerId(Integer customerId, Pageable pageable);
}
