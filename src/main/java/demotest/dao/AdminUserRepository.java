package demotest.dao;

import demotest.entity.AdminUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Created by simon on 2017/6/2.
 */
//@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "http://localhost:3000")
@RepositoryRestResource(collectionResourceRel = "adminuser", path = "adminuser")
public interface AdminUserRepository extends CrudRepository<AdminUser, Long>{
    AdminUser findByUsername(String username);
}
