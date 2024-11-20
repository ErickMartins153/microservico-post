package br.upe.base.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_comentario")
    private Post postId;

    @ManyToOne
    @JoinColumn(name = "id_dono")
    private Usuario donoId;

    @Column(name = "conteudo", nullable = false)
    private String conteudo;

    @Column(name = "curtidas", nullable = false)
    private int curtidas;

    @Column(name = "data_publicacao", nullable = false)
    private Instant dataPublicacao;
}
