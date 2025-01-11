package br.com.alura.screenmatch.desafios.desafioJPA;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class FornecedorDesafioOne2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;


    public FornecedorDesafioOne2(String nome){
        this.nome = nome;
    }
}
