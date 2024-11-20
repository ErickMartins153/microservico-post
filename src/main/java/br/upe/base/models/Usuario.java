package br.upe.base.models;

import java.util.List;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
public class Usuario {
    
    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    private UUID usuarioId;
    
    @Column(name = "nome")
    private String nome;
    
    @Column(name = "email", unique = true)
    private String email;
    
    @Column(name = "senha")
    private String senha;

    @JsonIgnore
    @OneToMany(mappedBy = "dono", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "Usuario_Seguidores",
        joinColumns = @JoinColumn(name = "usuarioId"),
        inverseJoinColumns = @JoinColumn(name = "seguidorId")
    )
    private List<Usuario> seguindo;

    @JsonIgnore
    @ManyToMany(mappedBy = "seguindo")
    private List<Usuario> seguidores;

}
