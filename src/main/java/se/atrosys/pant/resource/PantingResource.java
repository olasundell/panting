package se.atrosys.pant.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import se.atrosys.pant.model.Panting;
import se.atrosys.pant.repository.PantingRepository;

import java.util.List;

/**
 * TODO write documentation
 */
@RestController
public class PantingResource {
	private final PantingRepository pantingRepository;

	@Autowired
	public PantingResource(PantingRepository pantingRepository) {
		this.pantingRepository = pantingRepository;
	}

	@GetMapping(path = "/panting/{id}")
	public Panting getPanting(@PathVariable Integer id) {
		return pantingRepository.findOne(id);
	}

	@GetMapping(path = "/panting")
	public Page<Panting> getPantings(Pageable pageable) {
		return pantingRepository.findAll(pageable);
	}
}
