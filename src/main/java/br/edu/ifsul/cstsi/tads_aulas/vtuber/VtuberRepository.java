package br.edu.ifsul.cstsi.tads_aulas.vtuber;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VtuberRepository extends JpaRepository<Vtuber, String> {
    List<Vtuber> findByNomeStartingWith(String prefix);
}
