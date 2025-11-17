package br.edu.ifsul.cstsi.tads_aulas.autenticacao;

import br.edu.ifsul.cstsi.tads_aulas.usuario.Usuario;
import org.springframework.data.repository.Repository;
import org.springframework.security.core.userdetails.UserDetails; // ⬅️ Nova importação


public interface AutenticacaoRepository extends Repository<Usuario, Long> {
    UserDetails findByEmail(String email);
}