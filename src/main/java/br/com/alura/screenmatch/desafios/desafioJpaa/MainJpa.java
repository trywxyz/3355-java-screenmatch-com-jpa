//package br.com.alura.screenmatch.desafios.desafioJpaa;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class MainJpa {
//    @Autowired
//    private ProdutoRepository produtoRepository;
//    public static void main(String[] args) {
//
//
////        Iremos continuar colocando os conhecimentos sobre a JPA em prática. Dessa vez, iremos explorar melhor as derived queries. Para isso, utilize a aplicação que você criou na lista de exercícios da aula 2, a gerenciador-pedidos. Vamos lá?
////
////                Aqui, iremos apresentar uma descrição da busca que você deve fazer no banco de dados. Sua tarefa é decidir em qual dos repositórios essa busca melhor se encaixa, e criar a derived query correspondente. Caso tenha alguma dúvida sobre as consultas, consulte a documentação do Spring.
////
////        1 - Retorne todos os produtos com o nome exato fornecido.
////
////        2 - Retorne todos os produtos associados a uma categoria específica.
////
////        3 - Retorne produtos com preço maior que o valor fornecido.
////
////        4 - Retorne produtos com preço menor que o valor fornecido.
////
////        5 - Retorne produtos cujo nome contenha o termo especificado.
////
////        6 - Retorne pedidos que ainda não possuem uma data de entrega.
////
////        7 - Retorne pedidos com data de entrega preenchida.
////
////        8 - Retorne produtos de uma categoria ordenados pelo preço de forma crescente.
////
////        9 - Retorne produtos de uma categoria ordenados pelo preço de forma decrescente.
////
////        10 - Retorne a contagem de produtos em uma categoria específica.
////
////        11 - Retorne a contagem de produtos cujo preço seja maior que o valor fornecido.
////
////        12 - Retorne produtos com preço menor que o valor fornecido ou cujo nome contenha o termo especificado.
////
////        13 - Retorne pedidos feitos após uma data específica.
////
////        14 - Retorne pedidos feitos antes de uma data específica. , 15 - Retorne pedidos feitos em um intervalo de datas.
////
////        16 - Retorne os três produtos mais caros.
////
////        17 - Retorne os cinco produtos mais baratos de uma categoria.
////
////        Ver opinião do instrutor
////        Opinião do instrutor
////
////        Aqui temos algumas sugestões de implementação. Caso tenha feito de forma diferente, levante a discussão no fórum! Coloque sua solução por lá e discuta com a comunidade sobre as melhores formas de implementação :)
////
////        1 - Na classe ProdutoRepository:
////
////        List<Produto> findByNome(String nome)
////
////        2 - Na classe ProdutoRepository:
////
////        List<Produto> findByCategoriaNome(String categoriaNome)
////
////        3 - Na classe ProdutoRepository:
////
////        List<Produto> findByPrecoGreaterThan(Double preco)
////
////        4 - Na classe ProdutoRepository:
////
////        List<Produto> findByPrecoLessThan(Double preco)
////
////        5 - Na classe ProdutoRepository:
////
////        List<Produto> findByNomeContaining(String termo)
////
////        6 - Na classe PedidoRepository:
////
////        List<Pedido> findByDataEntregaIsNull()
////
////        7 - Na classe PedidoRepository:
////
////        List<Pedido> findByDataEntregaIsNotNull()
////
////        8 - Na classe ProdutoRepository:
////
////        List<Produto> findByCategoriaNomeOrderByPrecoAsc(String categoriaNome)
////
////        9 - Na classe ProdutoRepository:
////
////        List<Produto> findByCategoriaNomeOrderByPrecoDesc(String categoriaNome)
////
////        10 - Na classe ProdutoRepository:
////
////        long countByCategoriaNome(String categoriaNome)
////
////        11 - Na classe ProdutoRepository:
////
////        long countByPrecoGreaterThan(Double preco)
////
////        12 - Na classe ProdutoRepository:
////
////        List<Produto> findByPrecoLessThanOrNomeContaining(Double preco, String termo)
////
////        13 - Na classe PedidoRepository:
////
////        List<Pedido> findByDataPedidoAfter(LocalDate data)
////
////        14 - Na classe PedidoRepository:
////
////        List<Pedido> findByDataPedidoBefore(LocalDate data)
////
////        15 - Na classe PedidoRepository:
////
////        List<Pedido> findByDataPedidoBetween(LocalDate dataInicio, LocalDate dataFim)
////
////        16 - Na classe ProdutoRepository:
////
////        List<Produto> findTop3ByPrecoDesc()
////
////        17 - Na classe ProdutoRepository:
////
////        List<Produto> findTop5ByCategoriaNomeOrderByPrecoAsc(String categoriaNome)
//    }
//}
