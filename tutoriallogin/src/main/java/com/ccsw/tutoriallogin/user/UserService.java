package com.ccsw.tutoriallogin.user;

import com.ccsw.tutoriallogin.user.model.User;

public interface UserService {

    /**
     * Recupera un {@link User} a partir de su nombre
     *
     * @param name Nombre de la entidad
     * @return {@link User}
     */
    User get(String name);
}
