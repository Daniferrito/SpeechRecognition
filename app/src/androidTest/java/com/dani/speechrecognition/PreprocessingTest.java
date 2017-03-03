package com.dani.speechrecognition;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dani.speechrecognition.Preprocessing.deleteDuplicates;
import static com.dani.speechrecognition.Preprocessing.preprocessingSentences;
import static com.dani.speechrecognition.Preprocessing.replaceSymbols;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PreprocessingTest {

    @Test
    public void preprocessingValidator_replaceSymbols() {
        assertThat(replaceSymbols("123"), is("uno dos tres "));
        assertThat(replaceSymbols("1122"), is("uno uno dos dos "));
        assertThat(replaceSymbols("La 2 es grande."), is("La dos  es grande."));
    }

    @Test
    public void preprocessingValidator_deleteDuplicates() {
        ArrayList<String> list = new ArrayList<>();
        list.add("uno"); list.add("dos"); list.add("tres"); list.add("uno"); list.add("uno"); list.add("dos");
        deleteDuplicates(list);
        assertThat(list.size(), is(3));
        assertThat(list.get(0), is("uno"));
        assertThat(list.get(1), is("dos"));
        assertThat(list.get(2), is("tres"));
    }

    @Test
    public void preprocessingValidator_preprocessingSentences() {
        List<String> list = new ArrayList<>();
        list.add(" uno      Dos   ");
        list.add("12");
        list.add("2 tres");
        list.add("UNO");
        list = preprocessingSentences(list);
        assertThat(list.size(), is(3));
        assertThat(list.get(0), is("uno dos"));
        assertThat(list.get(1), is("dos tres"));
        assertThat(list.get(2), is("uno"));
    }

}
