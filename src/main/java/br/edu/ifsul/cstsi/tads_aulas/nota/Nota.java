package br.edu.ifsul.cstsi.tads_aulas.nota;

import br.edu.ifsul.cstsi.tads_aulas.usuario.Usuario;
import br.edu.ifsul.cstsi.tads_aulas.vtuber.Vtuber;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "notas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Nota {

    @Id // PK é String e deve ser atribuída manualmente
    private String uid; // Nome da propriedade é 'uid'

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vtuber_id", nullable = false, referencedColumnName = "uid", foreignKey = @ForeignKey(name = "fk_nota_vtuber"))
    private Vtuber vtuber;

    @ManyToMany
    @JoinTable(
            name = "nota_usuarios",
            // FK para Nota: referencia Nota.uid
            joinColumns = @JoinColumn(name = "nota_uid", referencedColumnName = "uid"),
            // FK para Usuario: referencia Usuario.uid
            inverseJoinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "uid"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"nota_uid", "usuario_id"})
    )
    private List<Usuario> usuarios = new ArrayList<>();

    private Integer valor;

    @Column(length = 2048)
    private String comentario;

    private Date dataCriacao;
    private Date dataAtualizacao;
}