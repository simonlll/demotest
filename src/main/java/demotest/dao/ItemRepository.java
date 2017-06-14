package demotest.dao;

import demotest.entity.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Created by simon on 2017/5/28.
 */

@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "http://localhost:3000")
@RepositoryRestResource(collectionResourceRel = "item", path = "item")
public interface ItemRepository extends CrudRepository<Item, Long>{

}
