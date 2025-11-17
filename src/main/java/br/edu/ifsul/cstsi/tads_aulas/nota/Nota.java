package br.edu.ifsul.cstsi.tads_aulas.nota;

import br.edu.ifsul.cstsi.tads_aulas.usuario.Usuario;
import br.edu.ifsul.cstsi.tads_aulas.vtuber.Vtuber;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity(name = "Nota")
@Table(name = "notas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Nota {

    @Id
    private String uid;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "vtuber_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_nota_vtuber"
            )
    )
    private Vtuber vtuber;

    @ManyToMany
    @JoinTable(
            name = "nota_usuarios",
            joinColumns = @JoinColumn(name = "nota_uid", referencedColumnName = "uid"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id") // Corrigir para "id"
    )
    private List<Usuario> usuarios = new ArrayList<>();

    private Integer valor;
    private String comentario;
    private Date dataCriacao;
    private Date dataAtualizacao;
}

