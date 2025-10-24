package br.edu.ifsul.cstsi.tads_aulas.infra.security;

import br.edu.ifsul.cstsi.tads_aulas.infra.exception.TokenInvalidoException;
import br.edu.ifsul.cstsi.tads_aulas.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.expirationHours:2}") // valor padrão de 2 horas
    private long tokenExpirationHours;

    // Gera o token JWT para o usuário
    public String geraToken(Usuario usuario) {
        try {
            // Usando o algoritmo HMAC com a chave secreta
            var algorithm = Algorithm.HMAC256(secret);

            // Gerando o token JWT
            return JWT.create()
                    .withIssuer("API Produtos Exemplo de TADS") // Emitido por
                    .withSubject(usuario.getEmail()) // Usando o email como subject
                    .withIssuedAt(Instant.now()) // Data de emissão do token
                    .withExpiresAt(Instant.now().plus(Duration.ofHours(tokenExpirationHours))) // Definindo tempo de expiração
                    .sign(algorithm); // Assinando o token
        } catch (JWTCreationException exception) {
            // Em caso de erro ao criar o token
            throw new RuntimeException("Erro ao gerar o token JWT.", exception);
        }
    }

    // Recupera o subject (e-mail do usuário) do token
    public String getSubject(String tokenJWT) {
        try {
            // Verificando e validando o token JWT
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("API Produtos Exemplo de TADS") // Verificando o issuer
                    .build()
                    .verify(tokenJWT) // Validando o token
                    .getSubject(); // Retorna o subject (e-mail do usuário)
        } catch (JWTVerificationException exception) {
            // Se o token for inválido ou expirado
            throw new TokenInvalidoException("Token JWT inválido ou expirado.");
        }
    }
}
