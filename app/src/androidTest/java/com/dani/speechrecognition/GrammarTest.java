package com.dani.speechrecognition;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GrammarTest {
    @Test
    public void gramarValidator_create_grammar() {
        //Vocabulary voc = new Vocabulary("uno","dos","tres","cuatro","cinco","seis","siete","ocho");
        //Grammar grammar = new Grammar(voc);
        Grammar grammar = new Grammar(1000);
        grammar.newRule("<INI>","<NUM>","uno","dos","tres","cuatro","cinco","seis","siete","ocho");
        grammar.newRule("<NUM>","<NUM>","uno","dos","tres","cuatro","cinco","seis","siete","ocho");
        grammar.newRule("<NUM>","<END>","uno","dos","tres","cuatro","cinco","seis","siete","ocho");

        assertThat( grammar.validSecuence("uno"), is(-1));
        assertThat( grammar.validSecuence("uno uno"), is(0));
        assertThat( grammar.validSecuence("uno dos tres"), is(0));
        assertThat( grammar.validSecuence("uno dos tres quatres"), is(0));
        //assertThat( grammar.validSecuence("uno dos tres nueve"), is(false));
    }

    @Test
    public void gramarValidator_bestSecuence_ReturnsCorrectSecuence() {
        Grammar grammar = new Grammar(1000);
        grammar.newRule("<INI>","<NUM>","uno","dos","tres","cuatro","cinco","seis","siete","ocho");
        grammar.newRule("<NUM>","<NUM>","uno","dos","tres","cuatro","cinco","seis","siete","ocho");
        grammar.newRule("<NUM>","<END>","uno","dos","tres","cuatro","cinco","seis","siete","ocho");
        ArrayList<String> list = new ArrayList<>();
        list.add("uno dos tres quatres");  // distancia edición =3
        list.add("unos do tres cuatro");  // distancia edición =2
        int bestScore = 0;
        String output = grammar.bestSecuence(list, bestScore);

        assertThat( output, is("unos do tres cuatro"));
        assertThat( bestScore, is(2));

    }
}
