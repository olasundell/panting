package se.atrosys.pant.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.atrosys.pant.exception.InvoiceNotFoundException;
import se.atrosys.pant.model.Invoice;
import se.atrosys.pant.model.InvoicingRequest;
import se.atrosys.pant.service.InvoiceService;

import java.util.List;

/**
 * TODO write documentation
 */
@RestController
public class InvoiceResource {
	private final InvoiceService service;

	@Autowired
	public InvoiceResource(InvoiceService service) {
		this.service = service;
	}

	@GetMapping(path="/statement/{customerId}/{year}/{month}")
	public Invoice getStatement(@PathVariable Integer customerId,
	                          @PathVariable Integer year,
	                          @PathVariable Integer month) {

		return service.getStatement(customerId, year, month);
	}

	@GetMapping(path="/invoice/{customerId}/{year}/{month}")
	public Invoice getInvoice(@PathVariable Integer customerId,
	                                    @PathVariable Integer year,
	                                    @PathVariable Integer month) {

		return service.getInvoice(customerId, year, month).orElseThrow(InvoiceNotFoundException::new);
	}

	@PutMapping(path="/invoice")
	public List<Invoice> createInvoices(@RequestBody InvoicingRequest invoicingRequest) {
		return service.createInvoices(invoicingRequest);
	}

	@GetMapping(path="/allinvoices/{customerId}")
	public List<Invoice> getInvoices(@PathVariable Integer customerId, Pageable pageable) {
		return service.getAllInvoices(customerId, pageable);
	}
}
