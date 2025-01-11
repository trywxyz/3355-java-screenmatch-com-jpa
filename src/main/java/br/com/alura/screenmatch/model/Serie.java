package br.com.alura.screenmatch.model;


import br.com.alura.screenmatch.service.traducaoSinopse.ConsultaMyMemory;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity //Cria uma tabela no banco de dados
@Table(name = "series") //Identifica a tabela com o nome 'series'
public class Serie {
    @Id //Chave primaria  = Long id;
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Estrategia de geração desse ID, AUTO: O JPA(Java Persistence API)  decide qual é o melhor, IDENTITY auto incremental.

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //@Column(name = "nomeDaSerie") Para caso já tivesse um banco de dados já existente e consegue criar verificações!
    @Column(unique = true)
    private String  titulo;
    private Integer totalTemporadas;
    private Double  avaliacao;

    @Enumerated(EnumType.STRING) //Enumerated - Diz qual é o tipo dessa enumaração (Ordinal pela ordem 0,1,2,3 ou String ex: Ação,drama,romance)
    private Categoria genero;
    private String  atores;
    private String  poster;
    private String  sinopse;

    //@Transient //Deixa esse atributo de lado, @Transient identifica que não vai acontecer nada com esse atributo
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER) //fetch = FetchType.EAGER diz como nossos relacionamentos são carregados ele busca pelas entidades mesma que não peça por elas(Busca Ansiosa)

    private List<Episodio> episodios = new ArrayList<>();

    public Serie(){

    }

    public Serie(DadosSerie dadosSerie){
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
        this.genero = Categoria.fromString(dadosSerie.genero().split("," )[0].trim());
        this.atores = dadosSerie.atores();
        this.poster = dadosSerie.poster();
        this.sinopse = ConsultaMyMemory.obterTraducao(dadosSerie.sinopse()).trim();
    }

    @Override
    public String toString() {
        return "Serie{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", totalTemporadas=" + totalTemporadas +
                ", avaliacao=" + avaliacao +
                ", genero=" + genero +
                ", atores='" + atores + '\'' +
                ", poster='" + poster + '\'' +
                ", sinopse='" + sinopse + '\'' +
                ", episodios=" + episodios +
                '}';
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;
    }
}
