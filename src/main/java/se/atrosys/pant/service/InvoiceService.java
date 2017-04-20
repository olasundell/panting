package se.atrosys.pant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.atrosys.pant.model.Invoice;
import se.atrosys.pant.model.Panting;
import se.atrosys.pant.repository.CustomerRepository;
import se.atrosys.pant.repository.InvoiceRepository;
import se.atrosys.pant.repository.PantingRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

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

	public Optional<Invoice> getInvoice(Integer customerId, Integer year, Integer month) {
		final Invoice invoice = invoiceRepository.findByCustomerIdAndPeriod(customerId, YearMonth.of(year, month));

		if (invoice != null) {
			invoice.setDefinitive(true);
		}

		return Optional.ofNullable(invoice);
	}

	public Invoice getStatement(Integer customerId, Integer year, Integer month) {
		List<Panting> found = getPantings(customerId, year, month);

		Double sum = found.stream()
				.mapToDouble(this::toDouble)
				.sum();

		return Invoice.builder()
				.amount(sum)
				.definitive(false)
				.customer(customerRepository.findOne(customerId))
				.period(YearMonth.of(year, month))
				.issuedAt(LocalDate.now())
				.build();
	}

	private List<Panting> getPantings(Integer customerId, Integer year, Integer month) {
		final LocalDate lower = LocalDate.of(year, month, 1);
		return pantingRepository.findByCustomerIdAndMadeAtIsBetween(customerId, lower, lower.plusMonths(1).minusDays(1));
	}

	private Double toDouble(Panting p) {
		return p.getContainer()
				.stream()
				.mapToDouble(c -> c.getPriceAt(p.getMadeAt()))
				.sum();
	}

	public Invoice createInvoiceForPeriod(Integer customerId, int year, int month) {
		Invoice invoice = getStatement(customerId, year, month);
		final Invoice save = invoiceRepository.save(invoice);
		save.setDefinitive(true);
		return save;
	}
}
