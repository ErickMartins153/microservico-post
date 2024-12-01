package br.upe.base.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "senha")
    private String senha;

    @JsonIgnore
    @OneToMany(mappedBy = "dono", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Post> posts;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "Usuario_Seguidores",
            joinColumns = @JoinColumn(name = "seguidoId"),
            inverseJoinColumns = @JoinColumn(name = "seguidorId")
    )
    private List<Usuario> seguindo;

    @JsonIgnore
    @ManyToMany(mappedBy = "seguindo")
    private List<Usuario> seguidores;

}
