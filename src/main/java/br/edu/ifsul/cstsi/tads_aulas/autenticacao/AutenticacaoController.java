package br.edu.ifsul.cstsi.tads_aulas.autenticacao;

import br.edu.ifsul.cstsi.tads_aulas.infra.security.TokenJwtDTO;
import br.edu.ifsul.cstsi.tads_aulas.infra.security.TokenService;
import br.edu.ifsul.cstsi.tads_aulas.usuario.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("api/v1/login")
public class AutenticacaoController {

    private final AutenticacaoService autenticacaoService;
    private final TokenService tokenService;

    public AutenticacaoController(AutenticacaoService autenticacaoService, TokenService tokenService) {
        this.autenticacaoService = autenticacaoService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenJwtDTO> efetuaLogin(@RequestBody UsuarioAutenticacaoDTO data) {
        try {
            // Tenta autenticar o usuário com o e-mail e senha fornecidos
            Usuario usuarioAutenticado = autenticacaoService.autenticar(data.email(), data.senha());

            // Gera o token JWT para o usuário autenticado
            String tokenJWT = tokenService.geraToken(usuarioAutenticado);

            // Retorna o token JWT como resposta
            return ResponseEntity.ok(new TokenJwtDTO(tokenJWT));
        } catch (IllegalArgumentException e) {
            // Retorna um erro 403 caso o usuário ou a senha estejam incorretos
            return ResponseEntity.status(403).body(null);
        }
    }
}

