package se.atrosys.pant.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import se.atrosys.pant.model.Invoice;

/**
 * TODO write documentation
 */
public interface InvoiceRepository extends PagingAndSortingRepository<Invoice, Integer> {
}
