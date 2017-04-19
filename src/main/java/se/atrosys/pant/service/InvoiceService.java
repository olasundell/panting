package se.atrosys.pant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.atrosys.pant.model.Invoice;
import se.atrosys.pant.model.Panting;
import se.atrosys.pant.repository.CustomerRepository;
import se.atrosys.pant.repository.InvoiceRepository;
import se.atrosys.pant.repository.PantingRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * TODO write documentation
 */
@Service
public class InvoiceService {
	private final InvoiceRepository invoiceRepository;
	private final CustomerRepository customerRepository;
	private final PantingRepository pantingRepository;

	@Autowired
	public InvoiceService(InvoiceRepository invoiceRepository,
	                      CustomerRepository customerRepository,
	                      PantingRepository pantingRepository) {

		this.invoiceRepository = invoiceRepository;
		this.customerRepository = customerRepository;
		this.pantingRepository = pantingRepository;
	}

	public Invoice getInvoice(Integer customerId, Integer year, Integer month) {
		final LocalDate lower = LocalDate.of(year, month, 1);
		List<Panting> found = pantingRepository.findByCustomerIdAndMadeAtIsBetween(customerId, lower, lower.plusMonths(1).minusDays(1));

		Double sum = found.stream()
				.mapToDouble(this::toDouble)
				.sum();

		return Invoice.builder()
				.amount(sum)
				.customer(customerRepository.findOne(customerId))
				.build();
	}

	private Double toDouble(Panting p) {
		return p.getContainer()
				.stream()
				.mapToDouble(c -> c.getPriceAt(p.getMadeAt()))
				.sum();
	}
}
