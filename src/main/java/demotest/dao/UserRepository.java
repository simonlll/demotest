package demotest.dao;

/**
 * Created by simon on 2017/5/23.
 */
//import org.springframework.data.repository.CrudRepository;
//
//
//
//// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
//// CRUD refers Create, Read, Update, Delete
//
//public interface UserRepository extends CrudRepository<User, Long> {
//
//}

import demotest.entity.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "http://localhost:3000")
@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends CrudRepository<User, Long> {



}
