package br.edu.ifsul.cstsi.tads_aulas.empresa;

import br.edu.ifsul.cstsi.tads_aulas.vtuber.Vtuber;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Empresa")
@Table(name = "empresas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Empresa {
    @Id
    private String uid;

    private String nome;
    private String urlFoto;

    private String info;

    @OneToMany(mappedBy = "empresa")
    private List<Vtuber> vtubers = new ArrayList<>();
}
