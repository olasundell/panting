package se.atrosys.pant.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import se.atrosys.pant.model.Invoice;
import se.atrosys.pant.service.InvoiceService;

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
}
