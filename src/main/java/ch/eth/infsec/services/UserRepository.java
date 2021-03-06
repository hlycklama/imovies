package ch.eth.infsec.services;

import ch.eth.infsec.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByEmail(String email);

    User findByUid(String uid);

}
