package se.atrosys.pant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se.atrosys.pant.model.Invoice;
import se.atrosys.pant.model.InvoicingRequest;
import se.atrosys.pant.model.Panting;
import se.atrosys.pant.repository.CustomerRepository;
import se.atrosys.pant.repository.InvoiceRepository;
import se.atrosys.pant.repository.PantingRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
		return getStatement(customerId, YearMonth.of(year, month));
	}

	protected Invoice getStatement(Integer customerId, YearMonth yearMonth) {
		List<Panting> found = getPantings(customerId, yearMonth);

		Double sum = found.stream()
				.mapToDouble(this::toDouble)
				.sum();

		return Invoice.builder()
				.amount(sum)
				.definitive(false)
				.customer(customerRepository.findOne(customerId))
				.period(yearMonth)
				.issuedAt(LocalDate.now())
				.build();
	}

	private List<Panting> getPantings(Integer customerId, YearMonth yearMonth) {
		return pantingRepository.findByCustomerIdAndMadeAtIsBetween(customerId, yearMonth.atDay(1), yearMonth.atEndOfMonth());
	}

	private Double toDouble(Panting p) {
		return p.getContainer()
				.stream()
				.mapToDouble(c -> c.getPriceAt(p.getMadeAt()))
				.sum();
	}

	public Invoice createInvoiceForPeriod(Integer customerId, int year, int month) {
		return this.createInvoiceForPeriod(customerId, YearMonth.of(year, month));
	}

	protected Invoice createInvoiceForPeriod(Integer customerId, YearMonth yearMonth) {
		Invoice invoice = getStatement(customerId, yearMonth);
		final Invoice save = invoiceRepository.save(invoice);
		save.setDefinitive(true);
		return save;
	}

	public List<Invoice> getAllInvoices(Integer customerId, Pageable pageable) {
		return invoiceRepository.findAllByCustomerId(customerId, pageable);
	}

	public List<Invoice> createInvoices(InvoicingRequest invoicingRequest) {
		return invoicingRequest.getPeriods()
				.stream()
				.map(ym -> createInvoiceForPeriod(invoicingRequest.getCustomerId(), ym))
				.collect(Collectors.toList());
	}
}
