package com.dani.speechrecognition;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static com.dani.speechrecognition.Vocabulary.editDistance;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class VocabularyTest {
    @Test
    public void vocabularyValidator_wordToSimbol_ReturnsCorrectSimbol() {
        Vocabulary voc = new Vocabulary("casa", "lápiz", "libro", "árbol");
        assertThat( voc.wordToSimbol("lápiz"), is(1));
    }
    @Test
    public void testVocabularyValidator_SimbolToWord_ReturnsCorrectWord() {
        Vocabulary voc = new Vocabulary("casa", "lápiz", "libro", "árbol");
        assertThat( voc.simbolToWord(1), is("lápiz"));
    }


    @Test
    public void testVocabularyValidator_varios() {
        Vocabulary voc = new Vocabulary("casa", "lápiz", "libro", "árbol");
        if (voc.wordToSimbol("lápiz") != 1) {
            //Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
        }
        if (!voc.simbolToWord(3).equals("árbol")) {
            //Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
        }

        if (editDistance("árbol", "arbo") != 2) {
            //Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
        }

        int[] simbols = voc.wordsToSimbols("casa árbo libro");
        String s = voc.simbolsTowords(simbols);
        if (!s.equals("casa árbol libro ")) {
            //Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
        }
    }
}


