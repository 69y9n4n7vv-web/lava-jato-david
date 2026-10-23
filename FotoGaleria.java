package com.lavajato.model;

import jakarta.persistence.*;

@Entity
public class FotoGaleria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String categoria;

    @Column(columnDefinition = "TEXT")
    private String urlImagem;

    public FotoGaleria() {}

    public FotoGaleria(String titulo, String categoria, String urlImagem) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.urlImagem = urlImagem;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getUrlImagem() { return urlImagem; }
    public void setUrlImagem(String urlImagem) { this.urlImagem = urlImagem; }
}