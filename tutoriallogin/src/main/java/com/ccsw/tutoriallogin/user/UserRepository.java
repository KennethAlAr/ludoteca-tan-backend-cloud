package com.ccsw.tutoriallogin.user;

import com.ccsw.tutoriallogin.user.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ccsw
 *
 */
public interface UserRepository extends CrudRepository<User, String> {

}
