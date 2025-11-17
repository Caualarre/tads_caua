package br.edu.ifsul.cstsi.tads_aulas.empresa;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/empresas")
public class EmpresaController {

    private final EmpresaRepository repository;

    public EmpresaController(EmpresaRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<EmpresaDTOResponse> create(@RequestBody @Valid EmpresaDTOPost dto) {
        try {

            Empresa novaEmpresa = new Empresa();
            novaEmpresa.setUid(dto.uid());
            novaEmpresa.setNome(dto.nome());
            novaEmpresa.setUrlFoto(dto.urlFoto());
            novaEmpresa.setInfo(dto.info());


            Empresa empresaSalva = repository.save(novaEmpresa);


            return ResponseEntity.status(HttpStatus.CREATED).body(new EmpresaDTOResponse(empresaSalva));

        } catch (Exception e) {

            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("{uid}")
    public ResponseEntity<EmpresaDTOResponse> update(@PathVariable String uid, @RequestBody @Valid EmpresaDTOPut dto) {
        // 1. Verifica se a empresa existe
        if (!repository.existsById(uid)) {
            return ResponseEntity.notFound().build();
        }

        Empresa empresaAtualizada = new Empresa();
        empresaAtualizada.setUid(uid);
        empresaAtualizada.setNome(dto.nome());
        empresaAtualizada.setUrlFoto(dto.urlFoto());
        empresaAtualizada.setInfo(dto.info());

        Empresa salva = repository.save(empresaAtualizada);

        return ResponseEntity.ok(new EmpresaDTOResponse(salva));
    }

    @GetMapping
    public ResponseEntity<List<EmpresaDTOResponse>> findAll(){
        var empresas = repository.findAll();

        return ResponseEntity.ok(EmpresaDTOResponse.fromEntityList(empresas));
    }

    @GetMapping("{id}")
    public ResponseEntity<EmpresaDTOResponse> findById(@PathVariable String id){
        var optional = repository.findById(id);
        if(optional.isPresent()){
            return ResponseEntity.ok(new EmpresaDTOResponse(optional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("nome/{nome}")
    public ResponseEntity<List<EmpresaDTOResponse>> findByNome(@PathVariable String nome){
        var empresas = repository.findByNomeStartingWith(nome);
        if(empresas.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(EmpresaDTOResponse.fromEntityList(empresas));
    }
}