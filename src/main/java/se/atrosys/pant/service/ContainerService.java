package se.atrosys.pant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.atrosys.pant.model.Container;
import se.atrosys.pant.repository.ContainerRepository;

/**
 * TODO write documentation
 */
@Service
public class ContainerService {
	private final ContainerRepository repository;

	@Autowired
	public ContainerService(ContainerRepository repository) {
		this.repository = repository;
	}

	public Container getContainer(Integer id) {
		return repository.findOne(id);
	}

	public Container createContainer(Container container) {
		Container saved = repository.save(container);
		return saved;
	}
}
