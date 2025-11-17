package br.edu.ifsul.cstsi.tads_aulas.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// ➡️ ID de Usuario é Long
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByNomeStartingWith(String prefix);
    Usuario findByEmail(String email);
}