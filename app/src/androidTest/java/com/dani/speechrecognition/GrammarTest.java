package com.dani.speechrecognition;

import android.util.Pair;

import org.junit.Test;

import java.util.ArrayList;

import static com.dani.speechrecognition.Grammar.DISTANCE_WHEN_NO_IN_GRAMMAR;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GrammarTest {
    @Test
    public void gramarValidator_validSecuence() {
        //Vocabulary voc = new Vocabulary("uno","dos","tres","cuatro","cinco","seis","siete","ocho");
        //Grammar grammar = new Grammar(voc);
        Grammar grammar = new Grammar(8); //8 = tamaño máximo de vocabulario
        grammar.newRule("<INI>", "<NUM>", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho");
        grammar.newRule("<NUM>", "<NUM>", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho");
        grammar.newRule("<NUM>", "<END>", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho");

        assertThat(grammar.validSecuence("uno"), is(DISTANCE_WHEN_NO_IN_GRAMMAR));
        assertThat(grammar.validSecuence("uno uno"), is(0));
        assertThat(grammar.validSecuence("uno dos tres"), is(0));
        assertThat(grammar.validSecuence("unos do tres cuatro"), is(2)); // unos>uno (1) do>dos (1)
        assertThat(grammar.validSecuence("uno dos tres quatres"), is(3)); // quatres > cuatro (3)
        assertThat(grammar.validSecuence("quatresa uno"), is(DISTANCE_WHEN_NO_IN_GRAMMAR));
        assertThat(grammar.validSecuence("12345"), is(DISTANCE_WHEN_NO_IN_GRAMMAR));
    }

    @Test
    public void gramarValidator_bestSecuenceAndScore_ReturnsCorrectSecuenceAndScore() {
        Grammar grammar = new Grammar(1000);
        grammar.newRule("<INI>", "<NUM>", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho");
        grammar.newRule("<NUM>", "<NUM>", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho");
        grammar.newRule("<NUM>", "<END>", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho");

        ArrayList<String> list = new ArrayList<>();
        list.add("uno dos tres quatres");  // distancia edición =3
        list.add("unos do tres cuatro");   // distancia edición =2
        String s = grammar.bestSecuence(list);
        assertThat(s, is("unos do tres cuatro"));

        Pair<String, Integer> p = grammar.bestSecuenceAndScore(list);
        assertThat(p.first, is("unos do tres cuatro"));
        assertThat(p.second, is(2));

        list = new ArrayList<>();
/*        list.add("Un dos tres cuatro");
        list.add("un dos tres cuatro");
        list.add("1 2 3 4");
*/        list.add("1234");
        list.add("uno dos tres cuatro");
        p = grammar.bestSecuenceAndScore(list);
        assertThat(p.first, is("uno dos tres cuatro"));
        assertThat(p.second, is(0));

    }
}
