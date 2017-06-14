package demotest.dao;

/**
 * Created by simon on 2017/5/23.
 */
import java.util.List;

import demotest.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;

@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "http://localhost:3000")
@RepositoryRestResource(collectionResourceRel = "customer", path = "customer")
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);
}
