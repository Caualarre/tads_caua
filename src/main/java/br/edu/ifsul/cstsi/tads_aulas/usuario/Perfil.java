package br.edu.ifsul.cstsi.tads_aulas.usuario;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
@Entity(name = "Perfil")
@Table(name = "perfis")
@NoArgsConstructor
@Getter
@Setter
public class Perfil implements GrantedAuthority {

    @Id // PK é String e deve ser atribuída manualmente
    private String uid; // ⚠️ Alterado de 'id' para 'uid' para consistência

    private String nome;

    @ManyToMany(mappedBy = "perfis")
    private List<Usuario> usuario;

    @Override
    public String getAuthority() {
        return nome;
    }
}