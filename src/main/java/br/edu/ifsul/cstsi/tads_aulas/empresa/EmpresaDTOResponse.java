package br.edu.ifsul.cstsi.tads_aulas.empresa;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO for {@link Empresa}
 */
public record EmpresaDTOResponse(
        String uid,
        String nome,
        String urlFoto,
        String info
) implements Serializable {


    public EmpresaDTOResponse(Empresa empresa) {

        this(
                empresa.getUid(),
                empresa.getNome(),
                empresa.getUrlFoto(),
                empresa.getInfo()
        );
    }


    public static List<EmpresaDTOResponse> fromEntityList(List<Empresa> empresas) {
        return empresas.stream()
                // Chama o construtor customizado para cada entidade
                .map(EmpresaDTOResponse::new)
                .collect(Collectors.toList());
    }
}