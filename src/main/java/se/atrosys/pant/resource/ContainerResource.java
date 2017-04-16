package se.atrosys.pant.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.atrosys.pant.model.Container;
import se.atrosys.pant.service.ContainerService;

/**
 * TODO write documentation
 */
@RestController
public class ContainerResource {
	private final ContainerService service;

	@Autowired
	public ContainerResource(ContainerService service) {
		this.service = service;
	}

	@GetMapping(path = "/container/{id}")
	public Container container(@PathVariable Integer id) {
		return service.getContainer(id);
	}

	@PostMapping(path = "/container")
	public Container createContainer(@RequestBody Container container) {
		return service.createContainer(container);
	}
}
