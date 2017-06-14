package demotest.dao;

import demotest.entity.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by simon on 2017/6/2.
 */
public interface RoleRepository extends CrudRepository<Role, Long>{
}
