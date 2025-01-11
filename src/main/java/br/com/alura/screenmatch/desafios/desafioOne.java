package br.com.alura.screenmatch.desafios;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class desafioOne {
    public static void main(String[] args) {

//1 - Imagine que você tem uma lista de strings. Algumas das strings são números, mas outras não. Queremos converter a lista de strings para uma lista de números. Se a conversão falhar, você deve ignorar o valor. Por exemplo, na lista a seguir, a saída deve ser [10, 20]:
        List<String> input = Arrays.asList("10", "abc", "20", "30x");
        List<Integer> inputModificado = input.stream().map(str -> {
            try {
                return Optional.of(Integer.parseInt(str));
            } catch (NumberFormatException e) {
                return Optional.<Integer>empty();
            }
        }).filter(Optional::isPresent).map(Optional::get).toList();

        System.out.println(inputModificado);
    }
}
