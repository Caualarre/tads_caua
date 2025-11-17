package br.edu.ifsul.cstsi.tads_aulas.autenticacao;

import br.edu.ifsul.cstsi.tads_aulas.infra.security.TokenJwtDTO;
import br.edu.ifsul.cstsi.tads_aulas.infra.security.TokenService;
import br.edu.ifsul.cstsi.tads_aulas.usuario.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/login")
public class AutenticacaoController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenJwtDTO> efetuaLogin(@RequestBody UsuarioAutenticacaoDTO data) {
        try {
            // Cria o token de autenticação com o login e senha brutos
            var authenticationToken = new UsernamePasswordAuthenticationToken(data.email(), data.senha());

            // 🔑 Delega a autenticação ao Spring Security
            Authentication authentication = manager.authenticate(authenticationToken);

            // Recupera o objeto do usuário autenticado (principal)
            Usuario usuarioAutenticado = (Usuario) authentication.getPrincipal();

            // Gera o token JWT
            String tokenJWT = tokenService.geraToken(usuarioAutenticado);

            // Retorna o token JWT
            return ResponseEntity.ok(new TokenJwtDTO(tokenJWT));
        } catch (AuthenticationException e) {
            // Retorna um erro 403 (Forbidden) caso a autenticação falhe
            return ResponseEntity.status(403).body(null);
        }
    }
}