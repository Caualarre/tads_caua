package br.edu.ifsul.cstsi.tads_aulas.empresa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmpresaDTOPost(

        @NotBlank(message = "O UID é obrigatório.")
        @Size(max = 255, message = "O UID deve ter no máximo 255 caracteres.")
        String uid, // Deve ser fornecido pelo cliente

        @NotBlank(message = "O nome é obrigatório.")
        @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres.")
        String nome,

        @Size(max = 500, message = "A URL da foto deve ter no máximo 500 caracteres.")
        String urlFoto,

        String info
) {
}