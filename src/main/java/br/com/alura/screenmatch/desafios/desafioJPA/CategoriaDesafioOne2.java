package br.com.alura.screenmatch.desafios.desafioJPA;


import br.com.alura.screenmatch.model.Categoria;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

//1 - Relacionamento @OneToMany (Categoria -> Produto):
public class CategoriaDesafioOne2 {

    @Id
    private Long id;
    private String nome;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<ProdutoDesafioOne2> produtos;

    public CategoriaDesafioOne2(Long id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<ProdutoDesafioOne2> getProdutos() {
        return produtos;
    }
}
