package com.daferto.common;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.daferto.common.Grammar.DISTANCE_WHEN_NO_IN_GRAMMAR;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GrammarTest2 {
    @Test
    public void gramarValidator_validSecuence() {
        //Vocabulary voc = new Vocabulary("uno","dos","tres","cuatro","cinco","seis","siete","ocho");
        //Grammar grammar = new Grammar(voc);
        Grammar grammar = new Grammar(8); //8 = tamaño máximo de vocabulario
        grammar.newRule("<INI>", "<NUM>", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho");
        grammar.newRule("<NUM>", "<NUM>", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho");
        grammar.newRule("<NUM>", "<END>", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho");

        assertThat(grammar.validSecuenceScore("uno"), is(DISTANCE_WHEN_NO_IN_GRAMMAR));
        assertThat(grammar.validSecuenceScore("uno uno"), is(0));
        assertThat(grammar.validSecuenceScore("uno dos tres"), is(0));
        assertThat(grammar.validSecuenceScore("unos do tres cuatro"), is(DISTANCE_WHEN_NO_IN_GRAMMAR)); // unos>uno (1) do>dos (1)
        assertThat(grammar.validSecuenceScore("uno dos tres quatres"), is(DISTANCE_WHEN_NO_IN_GRAMMAR)); // quatres > cuatro (3)
        assertThat(grammar.validSecuenceScore("quatresa uno"), is(DISTANCE_WHEN_NO_IN_GRAMMAR));
        assertThat(grammar.validSecuenceScore("12345"), is(DISTANCE_WHEN_NO_IN_GRAMMAR));
    }

    @Test
    public void gramarValidator_bestSecuenceAndScore_ReturnsCorrectSecuenceAndScore() {
        Grammar grammar = new Grammar(1000);
        grammar.newRule("<INI>", "<NUM>", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho");
        grammar.newRule("<NUM>", "<NUM>", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho");
        grammar.newRule("<NUM>", "<END>", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho");

        List<String> list = new ArrayList<>();
        list.add("uno dos tres quatres");  // distancia edición =3
        list.add("unos do tres cuatro");   // distancia edición =2
        String s = grammar.bestSecuence(list);
        assertThat(s, is("uno dos tres <NULL>"));

        Pair<String, Integer> p = grammar.bestSecuenceAndScore(list);
        assertThat(p.first, is("uno dos tres <NULL>"));
        assertThat(p.second, is(DISTANCE_WHEN_NO_IN_GRAMMAR));

        list = new ArrayList<>();
        list.add("miel dos");  // distancia edición =3
        list.add("ni el dos");   // distancia edición =2

        Pair<String, Integer> p2 = grammar.bestSecuenceAndScore(list);
        assertThat(p2.first, is("<NULL> dos"));
        assertThat(p2.second, is(DISTANCE_WHEN_NO_IN_GRAMMAR));

        list = new ArrayList<>();
        list.add("Un dos tres cuatro");
        list.add("un dos tres cuatro");
        list.add("1 2 3 4");
        list.add("1234");
        list.add("uno dos tres cuatro");
        list = Preprocessing.preprocessingSentences(list);
        p = grammar.bestSecuenceAndScore(list);
        assertThat(p.first, is("uno dos tres cuatro"));
        assertThat(p.second, is(0));

    }
}
