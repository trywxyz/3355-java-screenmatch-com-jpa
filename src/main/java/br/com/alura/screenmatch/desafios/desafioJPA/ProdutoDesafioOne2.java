package br.com.alura.screenmatch.desafios.desafioJPA;

import br.com.alura.screenmatch.model.Categoria;
import jakarta.persistence.*;

//2 - Configuração bidirecional do relacionamento, na classe Produto:

public class ProdutoDesafioOne2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    private String nome;
    private Double preco;

    public ProdutoDesafioOne2(String nome, Double preco, Categoria categoria){
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
    }
}
