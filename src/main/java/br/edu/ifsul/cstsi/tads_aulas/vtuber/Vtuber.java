package br.edu.ifsul.cstsi.tads_aulas.vtuber;

import br.edu.ifsul.cstsi.tads_aulas.empresa.Empresa;
import br.edu.ifsul.cstsi.tads_aulas.nota.Nota;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Vtuber")
@Table(name = "vtubers") // Nome padronizado no plural
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Vtuber {
    @Id
    private String uid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "empresa_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_vtuber_empresa")
    )
    private Empresa empresa;

    private String nome;
    private String urlFoto;

    private String info;

    private String linkCanal;
    private String videoYoutube;

    private float mediaNotas;

    private Integer totalAvaliacoes;

    private float somaTotalDasNotas;

    // Relação de composição forte: Vtuber possui suas notas e gerencia seu ciclo de vida
    @OneToMany(mappedBy = "vtuber", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nota> notas = new ArrayList<>();


}