package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, Double avaliacao);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria categoria);


    //As Duas Listas fazem a mesma coisa só que a mudança é que a lista menor puxa direto via banco de dados com linguagem de banco de dados enquanto outra e bem mais extensa que a outra pois tem uma logica por tras
    List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(int maximoDeTemporadas, double maximoDeAvalicao);

    //Utiliza linguagem nativa do banco de dados para fazer a requisição dos objetos
    @Query(value = "select * from series WHERE series.total_temporadas <= 5 AND series.avaliacao >= 7,5", nativeQuery = true)
    List<Serie> seriesPorTemporadaEAvaliacaoQuery();

    //JPQL Java Persistence Query Language
    @Query("select s from Serie s WHERE s.totalTemporadas <= :maximoDeTemporadas AND s.avaliacao >= :maximoDeAvalicao")
    List<Serie> seriesPorTemporadaEAvaliacao(int maximoDeTemporadas, double maximoDeAvalicao);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:trechoEpisodio")
    List<Episodio> episodioPorTrecho(String trechoEpisodio);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> topEpisodiosPorSerie(Serie serie);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie AND YEAR(e.dataLancamento) >= :anoLancamento  ")
    List<Episodio> episodiosPorSerieEAno(Serie serie, int anoLancamento);
}
