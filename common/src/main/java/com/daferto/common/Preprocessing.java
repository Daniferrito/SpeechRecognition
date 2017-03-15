package com.daferto.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class Preprocessing {

    public static final String[] REPLACE_SIMBOLS = {"0","cero ", "1","uno ", "2","dos ", "3","tres ",
            "4","cuatro ", "5","cinco ", "6","seis ", "7","siete ", "8","ocho ", "9","nueve ",
            "+","más ", "-","menos ", "/","barra "};

    public static String replaceSymbols(String in){
        String out = in;
        for (int i = 0; i< REPLACE_SIMBOLS.length; i+=2) {
            out = out.replace(REPLACE_SIMBOLS[i], REPLACE_SIMBOLS[i+1]);
        }
        return out;
    }

    public static <T> void deleteDuplicates(List<T> list) {
        Set<T> set = new LinkedHashSet<>(); //Para mantener los elementos ordenados
        set.addAll(list);
        list.clear();
        list.addAll(set);
    }

    public static List<String> preprocessingSentences(List<String> inputs) {
        List<String> list = new ArrayList<>();
        for (String s:inputs) {
            s = s.toLowerCase();                //A minúsculas
            s = replaceSymbols(s);              //Reemplazamos símbolos
            s = s.trim().replaceAll(" +", " "); //Eliminamos espacios duplicados
                                                //Eliminamos espacios principio y final
            if (!list.contains(s)) {            //Eliminamos frases duplicadas
                list.add(s);
            }
        }
//        deleteDuplicates(list);                 //Eliminamos frases duplicadas
        return list;
    }

}
