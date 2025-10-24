package br.edu.ifsul.cstsi.tads_aulas.usuario;

import br.edu.ifsul.cstsi.tads_aulas.vtuber.Vtuber;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;  // Importa a classe SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;  // Importa Collectors

@Entity(name = "Usuario")
@Table(name = "usuarios")
@NoArgsConstructor
@Getter
@Setter
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    @JoinTable(
            name = "usuario_vtuber_favoritos",
            joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "vtuber_uid", referencedColumnName = "uid")
    )
    private List<Vtuber> favoritos;

    @ManyToMany
    @JoinTable(
            name = "usuario_perfil",
            joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "perfil_id", referencedColumnName = "id")
    )
    private List<Perfil> perfis;  // Alterado de 'favoritos' para 'perfis'

    private String nome;
    private String sobrenome;

    @Column(unique = true)
    private String email;

    private String senha;

    private boolean isConfirmado = false;

    // Método getAuthorities agora usa os Perfis associados
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna as permissões dos perfis associados ao usuário, agora com SimpleGrantedAuthority
        return perfis.stream()
                .map(perfil -> new SimpleGrantedAuthority(perfil.getAuthority())) // Usa SimpleGrantedAuthority
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
