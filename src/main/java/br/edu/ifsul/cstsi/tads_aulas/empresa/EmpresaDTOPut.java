package br.edu.ifsul.cstsi.tads_aulas.empresa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EmpresaDTOPut(

        // O UID é necessário para identificar o recurso a ser atualizado
        @NotNull(message = "O UID da empresa é obrigatório para atualização.")
        String uid,

        @NotBlank(message = "O nome é obrigatório.")
        @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres.")
        String nome,

        @Size(max = 500, message = "A URL da foto deve ter no máximo 500 caracteres.")
        String urlFoto,

        String info
) {
}