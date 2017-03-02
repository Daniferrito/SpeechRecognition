package com.dani.speechrecognition;

import android.util.Pair;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GrammarTest {
    @Test
    public void gramarValidator_validSecuence() {
        //Vocabulary voc = new Vocabulary("uno","dos","tres","cuatro","cinco","seis","siete","ocho");
        //Grammar grammar = new Grammar(voc);
        Grammar grammar = new Grammar(1000);
        grammar.newRule("<INI>","<NUM>","uno","dos","tres","cuatro","cinco","seis","siete","ocho");
        grammar.newRule("<NUM>","<NUM>","uno","dos","tres","cuatro","cinco","seis","siete","ocho");
        grammar.newRule("<NUM>","<END>","uno","dos","tres","cuatro","cinco","seis","siete","ocho");

        assertThat( grammar.validSecuence("uno"), is(-1));
        assertThat( grammar.validSecuence("uno uno"), is(0));
        assertThat( grammar.validSecuence("uno dos tres"), is(0));
        assertThat( grammar.validSecuence("unos do tres cuatro"), is(2)); // unos>uno (1) do>dos (1)
        assertThat( grammar.validSecuence("uno dos tres quatres"), is(3)); // quatres > cuatro (3)
//        assertThat( grammar.validSecuence("uno dos tres quatresa"), is(-1));
        assertThat( grammar.validSecuence("quatresa uno"), is(-1));
    }

    @Test
    public void gramarValidator_bestSecuenceAndScore_ReturnsCorrectSecuenceAndScore() {
        Grammar grammar = new Grammar(1000);
        grammar.newRule("<INI>","<NUM>","uno","dos","tres","cuatro","cinco","seis","siete","ocho");
        grammar.newRule("<NUM>","<NUM>","uno","dos","tres","cuatro","cinco","seis","siete","ocho");
        grammar.newRule("<NUM>","<END>","uno","dos","tres","cuatro","cinco","seis","siete","ocho");
        ArrayList<String> list = new ArrayList<>();
        list.add("uno dos tres quatres");  // distancia edición =3
        list.add("unos do tres cuatro");   // distancia edición =2

        Pair<String,Integer> p = grammar.bestSecuenceAndScore(list);
        assertThat( p.first, is("unos do tres cuatro"));
        assertThat( p.second, is(2));

        String s = grammar.bestSecuence(list);
        assertThat( s, is("unos do tres cuatro"));
    }
}
