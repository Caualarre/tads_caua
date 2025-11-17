package br.edu.ifsul.cstsi.tads_aulas.usuario;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@NoArgsConstructor
@Getter
@Setter
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "usuario_vtuber_favoritos",
            joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "vtuber_uid")
    private List<String> favoritos;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_perfil",
            joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "perfil_id", referencedColumnName = "id")
    )
    private List<Perfil> perfis;

    private String nome;
    private String sobrenome;

    @Column(unique = true)
    private String email;

    private String senha;

    private boolean isConfirmado = false;

    // Métodos de UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfis.stream()
                .map(perfil -> new SimpleGrantedAuthority(perfil.getAuthority()))
                .collect(Collectors.toList());
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

    // Método para criptografar a senha (usado ao cadastrar)
    public void setSenha(String senha) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        this.senha = encoder.encode(senha);
    }
}