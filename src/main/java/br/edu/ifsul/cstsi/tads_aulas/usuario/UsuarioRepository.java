package br.edu.ifsul.cstsi.tads_aulas.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    List<Usuario> findByNomeStartingWith(String prefix);
    Usuario findByEmail(String email);
}