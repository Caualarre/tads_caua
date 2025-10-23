package br.edu.ifsul.cstsi.tads_aulas.empresa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpresaRepository extends JpaRepository<Empresa, String> {
    List<Empresa> findByNomeStartingWith(String prefix);
}
