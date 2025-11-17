package br.edu.ifsul.cstsi.tads_aulas.usuario;

import br.edu.ifsul.cstsi.tads_aulas.nota.Nota;
import br.edu.ifsul.cstsi.tads_aulas.vtuber.Vtuber;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@NoArgsConstructor
@Getter
@Setter
public class Usuario implements UserDetails {

    @Id // PK é String e deve ser atribuída manualmente
    private String uid;


    // Relação com Vtuber (Favoritos)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_favoritos",
            joinColumns = @JoinColumn(name = "usuario_uid", referencedColumnName = "uid", foreignKey = @ForeignKey(name = "fk_fav_usuario")),
            inverseJoinColumns = @JoinColumn(name = "vtuber_uid", referencedColumnName = "uid", foreignKey = @ForeignKey(name = "fk_fav_vtuber")),
            uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_uid", "vtuber_uid"})
    )
    private List<Vtuber> favoritos = new ArrayList<>();

    // Relação com Nota (Inverso)
    @ManyToMany(mappedBy = "usuarios", fetch = FetchType.LAZY)
    private List<Nota> notas = new ArrayList<>();

    // Relação com Perfil
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_perfil",
            // FK para Usuario: referenciado pelo nome da PK 'uid'
            joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "uid",
                    foreignKey = @ForeignKey(name = "fk_usuario_perfil_usuario")),
            // FK para Perfil: referenciado pelo nome da PK 'uid' de Perfil (presumindo Perfil também foi alterado para String uid)
            inverseJoinColumns = @JoinColumn(name = "perfil_id", referencedColumnName = "uid",
                    foreignKey = @ForeignKey(name = "fk_usuario_perfil_perfil")),
            uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "perfil_id"})
    )
    private List<Perfil> perfis = new ArrayList<>();

    private String nome;
    private String sobrenome;

    @Column(unique = true)
    private String email;

    private String senha;
    private boolean isConfirmado = false;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfis.stream()
                .map(perfil -> new SimpleGrantedAuthority(perfil.getAuthority()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() { return senha; }
    @Override
    public String getUsername() { return email; }
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return isConfirmado; }

    public void setSenha(String senha) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        this.senha = encoder.encode(senha);
    }
}