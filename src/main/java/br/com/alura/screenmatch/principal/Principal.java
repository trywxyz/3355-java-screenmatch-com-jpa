package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.*;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private SerieRepository repository;

    public Principal(SerieRepository repository) {
        this.repository = repository;

    }

    private List<Serie> series = new ArrayList<>();

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=3d608cd1";

    private List<DadosSerie> dadosSerie =  new ArrayList<>();
    private Optional<Serie> serieBusca;




    public void exibeMenu() {
        var opcao = -1;
        while(opcao != 0){
            var menu = """
                1 - Buscar séries
                2 - Buscar episódios
                3 - Listar séries buscadas 
                4 - Buscar série por titulo
                5 - Buscar séries por ator
                6 - Top 5 séries
                7 - Buscar séries por categoria
                8 - Buscar por maximo de temporadas e avaliação minima
                9 - Busca episodios por trecho
                10 - Busca Top 5 episódios por série
                11 - Buscar episódios a partir de uma data
                0 - Sair                                 
                """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriePorAtor();
                    break;
                case 6:
                    buscarTop5Series();
                    break;
                case 7:
                    buscarSeriesPorCategoria();
                    break;
                case 8:
                    buscaPorMaximoDeTemporadasEAvalicao();
                    break;
                case 9:
                    buscaEpisodioPorTrecho();
                    break;
                case 10:
                    topEpisodiosPorSerie();
                    break;
                case 11:
                    buscarEpisodiosAPartirDeUmaData();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
//        dadosSerie.add(dados);
        repository.save(serie);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie() {
//        DadosSerie dadosSerie = getDadosSerie();
        listarSeriesBuscadas();
        System.out.println("Escolha uma serie pelo nome: ");
        var nomeSerie = leitura.nextLine();

        //series.stream().filter(s -> s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase())).findFirst();
        serieBusca = repository.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBusca.isPresent()) {
            var serieEncontrada = serieBusca.get();
            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios =  temporadas.stream().flatMap(d -> d.episodios().stream().map(e -> new Episodio(d.numero(), e))).collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);

            repository.save(serieEncontrada);
        }else{
            System.out.println("Serie não encontrada!");
        }
    }

    private void listarSeriesBuscadas(){
        List<Serie> series = repository.findAll();
//      series = dadosSerie.stream().map(d -> new Serie(d)).collect(Collectors.toList());
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriePorTitulo() {
        System.out.println("Escolha uma série pelo nome: ");
        var nomeSerie = leitura.nextLine();
        Optional<Serie> serieBuscada = repository.findByTituloContainingIgnoreCase(nomeSerie);


        if(serieBuscada.isPresent()){
            System.out.println("Dados da série "+ serieBuscada.get());
        }else{
            System.out.println("Serie não encontrada!");
        }
    }

    private void buscarSeriePorAtor() {
        System.out.println("Escolha uma série por ator:");
        var nomeAtor = leitura.nextLine();
        System.out.println("Avaliações a partir de que nota? {Ex: 8,8}");
        var avaliacao = leitura.nextDouble();

        List<Serie> serieEncontradaPorAtor = repository.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacao);
        System.out.println("Series em que **" + nomeAtor + "** trabalhou: ");
        serieEncontradaPorAtor.forEach(s -> System.out.println(s.getTitulo() + "Avaliação: " + s.getAvaliacao()));
    }

    private void buscarTop5Series() {
        List<Serie> serieTop = repository.findTop5ByOrderByAvaliacaoDesc();
        serieTop.forEach(s -> System.out.println(s.getTitulo() + " avaliação: " + s.getAvaliacao() ));
    }

    private void buscarSeriesPorCategoria(){
        System.out.println("Deseja buscar séries por qual categoria/gênero? Ex: Romance,Comedia");
        var nomeGenero = leitura.nextLine();
        Categoria  categoria = Categoria.fromStringPortugues(nomeGenero);
        List<Serie> seriePorCategoria = repository.findByGenero(categoria);
        seriePorCategoria.forEach(System.out::println);
    }

    private void buscaPorMaximoDeTemporadasEAvalicao(){
        System.out.println("Deseja buscar séries com qual quantidade de número maximo de temporadas? Ex:{3}");
        var maximoDeTemporadas = leitura.nextInt();
//        leitura.nextLine();
        System.out.println("Qual a avaliação minima desejada para a série ? Ex:{8,8}");
        var maximoDeAvalicao = leitura.nextDouble();
//        leitura.nextLine();
//        List<Serie> filtroSeries = repository.findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(maximoDeTemporadas, maximoDeAvalicao);
        List<Serie> filtroSeries = repository.seriesPorTemporadaEAvaliacao(maximoDeTemporadas, maximoDeAvalicao);
        System.out.println("*** Séries filtradas ***");
        filtroSeries.forEach(f -> System.out.println(f.getTitulo() + " - Avaliaçã: " + f.getAvaliacao()));

    }

    private void buscaEpisodioPorTrecho(){
        System.out.println("Qual nome do episodio para busca? ");
        var trechoEpisodio = leitura.nextLine();
        List<Episodio> episodiosEncontrados = repository.episodioPorTrecho(trechoEpisodio);
        episodiosEncontrados.forEach(e -> System.out.printf("Série: %s Temporada %s - Episodio %s - %s \n", e.getSerie(), e.getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()));
    }

    private void topEpisodiosPorSerie(){
        buscarSeriePorTitulo();
        if(serieBusca.isPresent()){
            Serie serie = serieBusca.get();
            List<Episodio> topEpisodios = repository.topEpisodiosPorSerie(serie);

            topEpisodios.forEach(e -> System.out.printf("Série: %s Temporada %s - Episódio %s - %s \n", e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo(), e.getAvaliacao() ));
        }
    }

    private void buscarEpisodiosAPartirDeUmaData(){
        buscarSeriePorTitulo();
        if(serieBusca.isPresent()){
            Serie serie = serieBusca.get();
            System.out.println("Digite o ano limite de lançamento");
            var anoLancamento = leitura.nextInt();
            leitura.nextLine();

            List<Episodio> episodiosPorAno = repository.episodiosPorSerieEAno(serie, anoLancamento);
            episodiosPorAno.forEach(System.out::println);
        }
    }





}