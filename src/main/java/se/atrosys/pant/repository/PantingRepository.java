package se.atrosys.pant.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import se.atrosys.pant.model.Panting;

/**
 * TODO write documentation
 */
public interface PantingRepository extends PagingAndSortingRepository<Panting, Integer> {
}
