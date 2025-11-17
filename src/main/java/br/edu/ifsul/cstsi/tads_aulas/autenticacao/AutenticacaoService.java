package br.edu.ifsul.cstsi.tads_aulas.autenticacao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService; // ⬅️ Nova importação
import org.springframework.security.core.userdetails.UsernameNotFoundException; // ⬅️ Nova importação
import org.springframework.stereotype.Service;

@Service
// ➡️ Implementa UserDetailsService
public class AutenticacaoService implements UserDetailsService {

    private final AutenticacaoRepository rep;

    // Não precisa mais do BCryptPasswordEncoder no construtor
    public AutenticacaoService(AutenticacaoRepository rep) {
        this.rep = rep;
    }

    //  Implementação obrigatória do UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Encontra o usuário pelo e-mail (username)
        UserDetails usuario = rep.findByEmail(username);

        if (usuario == null) {
            // Lança a exceção padrão que o Spring Security sabe tratar
            throw new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + username);
        }

        return usuario; // Retorna o objeto Usuario (que implementa UserDetails)
    }
}