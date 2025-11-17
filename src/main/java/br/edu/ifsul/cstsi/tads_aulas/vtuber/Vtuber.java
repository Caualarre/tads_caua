package br.edu.ifsul.cstsi.tads_aulas.vtuber;

import br.edu.ifsul.cstsi.tads_aulas.empresa.Empresa;
import br.edu.ifsul.cstsi.tads_aulas.nota.Nota;
import br.edu.ifsul.cstsi.tads_aulas.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vtubers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Vtuber {

    @Id // PK é String e deve ser atribuída manualmente
    private String uid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empresa_id", nullable = false, referencedColumnName = "uid", foreignKey = @ForeignKey(name = "fk_vtuber_empresa"))
    private Empresa empresa;

    private String nome;
    private String urlFoto;
    private String info;
    private String linkCanal;
    private String videoYoutube;

    private float mediaNotas;
    private Integer totalAvaliacoes;
    private float somaTotalDasNotas;

    @OneToMany(mappedBy = "vtuber", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nota> notas = new ArrayList<>();

    @ManyToMany(mappedBy = "favoritos", fetch = FetchType.LAZY)
    private List<Usuario> favoritedBy = new ArrayList<>();
}