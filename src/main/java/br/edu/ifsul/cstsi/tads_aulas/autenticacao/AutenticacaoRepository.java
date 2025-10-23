package br.edu.ifsul.cstsi.tads_aulas.autenticacao;

import br.edu.ifsul.cstsi.tads_aulas.usuario.Usuario;
import org.springframework.data.repository.Repository;

public interface AutenticacaoRepository extends Repository<Usuario, Long> {
    Usuario findByEmail(String email);
}
