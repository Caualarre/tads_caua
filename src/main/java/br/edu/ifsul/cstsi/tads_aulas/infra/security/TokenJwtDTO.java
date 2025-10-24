package br.edu.ifsul.cstsi.tads_aulas.infra.security;

public class TokenJwtDTO {

    private String token;

    public TokenJwtDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
