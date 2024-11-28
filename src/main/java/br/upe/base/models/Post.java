package br.upe.base.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "dono_id")
    private Usuario dono;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "conteudo", nullable = false)
    private String conteudo;

    @Column(name = "curtidas", nullable = false)
    private int curtidas;

    @Column(name = "comentarios")
    @JsonIgnore
    @OneToMany(mappedBy = "dono", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Comentario> comentarios;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "post_hashtags", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "hashtag", nullable = false)
    private Set<String> hashTags = new HashSet<>();

    @Column(name = "data_publicacao", nullable = false)
    private Instant dataPublicacao;

    public void addHashTag(String hashTag) {
        hashTags.add(hashTag);
    }

    public void deleteHashTag(String hashTag) {
        hashTags.remove(hashTag);
    }
}
