package br.com.alura.screenmatch.model;

public enum Categoria {
    ACAO("Action", "Ação"),
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy", "Comédia"),
    DRAMA("Drama", "Drama"),
    CRIME("Crime", "Crime");

    private String categoriaOmdb;

    private String categoriaOmdbPortugues;

    Categoria(String categoriaOmdb, String categoriaOmdbPortugues){
       this.categoriaOmdb = categoriaOmdb;
       this.categoriaOmdbPortugues = categoriaOmdbPortugues;

    }


    public static Categoria fromString(String text){
        for(Categoria categoria : Categoria.values()){
            if(categoria.categoriaOmdb.equalsIgnoreCase(text)){
                return categoria;

            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada!");
    }

    public static Categoria fromStringPortugues(String text){
        for(Categoria categoria : Categoria.values()){
            if(categoria.categoriaOmdbPortugues.equalsIgnoreCase(text)){
                return categoria;

            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada!");
    }


}
