package br.edu.ifsul.cstsi.tads_aulas.autenticacao;

import br.edu.ifsul.cstsi.tads_aulas.usuario.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService {

    private final AutenticacaoRepository rep;
    private final BCryptPasswordEncoder passwordEncoder;

    public AutenticacaoService(AutenticacaoRepository rep, BCryptPasswordEncoder passwordEncoder) {
        this.rep = rep;
        this.passwordEncoder = passwordEncoder;
    }

    // Autentica um usuário com base no email e senha
    public Usuario autenticar(String email, String senha) {
        // Encontra o usuário pelo e-mail
        Usuario usuario = rep.findByEmail(email);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado com o e-mail: " + email);
        }

        // Verifica se a senha fornecida é válida comparando com a senha criptografada
        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new IllegalArgumentException("Senha inválida");
        }

        return usuario; // Retorna o usuário autenticado
    }
}
