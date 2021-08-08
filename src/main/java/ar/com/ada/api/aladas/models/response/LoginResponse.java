package ar.com.ada.api.aladas.models.response;

import ar.com.ada.api.aladas.entities.Usuario.TipoUsuarioEnum;

public class LoginResponse {

    public Integer id;
    public String username;
    public String token;
    public String email;
    public TipoUsuarioEnum userType;
    public Integer entityId;
    
}
