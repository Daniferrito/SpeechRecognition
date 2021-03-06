package com.daferto.common;

import org.junit.Test;

import static com.daferto.common.Vocabulary.DISTANCE_WHEN_HIGHER_MAX_EDIT;
import static com.daferto.common.Vocabulary.editDistance;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class VocabularyTest {

    @Test
    public void vocabularyValidator_editDistance() {
        assertThat(editDistance("árbol", "arbol"), is(1));
        assertThat(editDistance("á", "a"), is(1));

        assertThat(editDistance("árbol", "arbo"), is(2));
        assertThat(editDistance("árbol", "árbols"), is(1));

        assertThat(editDistance("lápiz", "lápiz"), is(0));
        assertThat(editDistance("árbol", "arbo"), is(2));
    }

    @Test
    public void vocabularyValidator_SimbolToWord_ReturnsCorrectWord() {
        Vocabulary voc = new Vocabulary("casa", "lápiz", "libro", "árbol");
        assertThat(voc.simbolToWord(1), is("lápiz"));
    }

    @Test
    public void vocabularyValidator_wordToSimbol_ReturnsCorrectSimbol() {
        Vocabulary voc = new Vocabulary("casa", "lápiz", "libro", "árbol");
        assertThat(voc.wordToSimbol("casa"), is(0));
        assertThat(voc.wordToSimbol("lápiz"), is(1));
        assertThat(voc.wordToSimbol("árbol"), is(3));
    }

    @Test
    public void vocabularyValidator_wordToSimbol_with_editDistance() {
        Vocabulary voc = new Vocabulary("casa", "lápiz", "libro", "árbol");

        Pair<Integer, int[]> p = voc.wordsToSimbols("cassa");



        assertThat(p.first, is(1));
        assertThat(p.second.length, is(1));
        assertThat(p.second[0], is(0));
        p = voc.wordsToSimbols("lápiz arbo");
        assertThat(p.first, is(DISTANCE_WHEN_HIGHER_MAX_EDIT));
        assertThat(p.second.length, is(2));
        assertThat(p.second[0], is(1));
        assertThat(p.second[1], is(-1));
    }

    @Test
    public void vocabularyValidator_wordsToSimbols() {
        Vocabulary voc = new Vocabulary("casa", "lápiz", "libro", "árbol");
        Pair<Integer,int[]> p = voc.wordsToSimbols("casa árbo libro");
        String s = voc.simbolsToWords(p.second);
        assertThat(s, is("casa árbol libro"));
        assertThat(p.first, is(1));
    }
}