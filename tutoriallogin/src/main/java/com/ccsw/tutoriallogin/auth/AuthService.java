package com.ccsw.tutoriallogin.auth;

import com.ccsw.tutoriallogin.auth.model.LoginDto;
import com.ccsw.tutoriallogin.auth.model.LoginResponseDto;

public interface AuthService {

    /**
     * Crea un {@link LoginResponseDto} a partir de un LoginDto
     *
     * @param dto LoginDto de la petición
     * @return {@link LoginResponseDto}
     */
    LoginResponseDto login(LoginDto dto);
}
