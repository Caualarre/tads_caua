package br.edu.ifsul.cstsi.tads_aulas.empresa;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/empresas")
public class EmpresaController {

    private final EmpresaRepository repository;

    public EmpresaController(EmpresaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Empresa>> findAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Empresa> findById(@PathVariable String id){
        var optional = repository.findById(id);
        if(optional.isPresent()){
            return ResponseEntity.ok(optional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("nome/{nome}")
    public ResponseEntity<List<Empresa>> findByNome(@PathVariable String nome){
        var empresas = repository.findByNomeStartingWith(nome);
        if(empresas.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(empresas);
    }
}
