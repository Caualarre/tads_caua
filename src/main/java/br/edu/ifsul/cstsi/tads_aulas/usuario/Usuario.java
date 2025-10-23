package br.edu.ifsul.cstsi.tads_aulas.usuario;

import br.edu.ifsul.cstsi.tads_aulas.vtuber.Vtuber;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@NoArgsConstructor
@Getter
@Setter
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String sobrenome;

    @Column(unique = true)
    private String email;

    private String senha;

    private boolean isConfirmado = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuarios_perfis",
            joinColumns = @JoinColumn(name = "usuarios_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "perfis_id", referencedColumnName = "id"))
    private List<Perfil> perfis;

    // O campo "favoritos" em Usuario, que agora estará presente para o mapeamento inverso de Vtuber
    @ManyToMany
    @JoinTable(
            name = "usuario_vtuber_favoritos",
            joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "vtuber_uid", referencedColumnName = "uid")
    )
    private List<Vtuber> favoritos;

    // Métodos implementados da interface UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfis;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isConfirmado;
    }

    // Método para criptografar a senha
    public void setSenha(String senha) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        this.senha = encoder.encode(senha);
    }

    // Método para verificar se a senha fornecida é válida
    public boolean verificarSenha(String senha) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(senha, this.senha);
    }
}


