package com.dani.speechrecognition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jesús Tomás on 16/02/2017.
 */

public class Vocabulary {

    Map<String, Integer> mapWords;
    List<String> listWords;

    public Vocabulary() {
        mapWords = new HashMap<>();
        listWords = new ArrayList<>();
    }

    public Vocabulary(int length) {
        mapWords = new HashMap<>(length);
        listWords = new ArrayList<>(length);
    }

    public Vocabulary(String... words) {
        this(words.length);
        for (int i = 0; i < words.length; i++) {
            mapWords.put(words[i], i);
            listWords.add(i, words[i]);
        }
    }

    public int wordToSimbol(String word) {
        try {
            return mapWords.get(word);
        } catch (Exception e) {
            return -1;
        }
    }

    public String simbolToWord(int simbol) {
        return listWords.get(simbol);
    }

    String simbolsTowords(int[] simbols) {
        String words = "";
        for (int i = 0; i < simbols.length; i++) {
            words += listWords.get(simbols[i]) + " ";
        }
        return words;
    }

    int[] wordsToSimbols(String phase) {
        String[] words = phase.split(" ");
        int[] simbols = new int[words.length];
        for (int i = 0; i < words.length; i++) {
            simbols[i] = wordToSimbol(words[i]);
            if (simbols[i] == -1) { //buscamos palbra más parecida
                int bestDistance = 100;
                int bestSimbol = -1;
                for (int j = 0; j < listWords.size(); j++) {
                    int d = editDistance(listWords.get(j), words[i]);
                    if (d < bestDistance) {
                        bestDistance = d;
                        bestSimbol = j;
                    }
                }
                if (bestDistance <= 3) {
                    simbols[i] = bestSimbol;
                }
            }
        }
        return simbols;
    }

    public static int min(int a, int b, int c) {
        if (a < b) {
            return Math.min(a, c);
        } else { // b<=a
            return Math.min(b, c);
        }
    }

    public static int editDistance(String target, String source) {
        int n = target.length();
        int m = source.length();
        int[][] distance = new int[n][m];
        for (int i = 0; i < n; i++) distance[i][0] = i;
        for (int j = 0; j < m; j++) distance[0][j] = j;
        for (int i = 1; i < n; i++)
            for (int j = 1; j < m; j++)
                distance[i][j] =
                        min(distance[i - 1][j] + 1,
                                distance[i - 1][j - 1] +
                                        (target.charAt(i) == source.charAt(j) ? 0 : 2),
                                distance[i][j - 1] + 1);
        return distance[n - 1][m - 1];
    }

    public int add(String word) {
        int simbol = size();
        mapWords.put(word, simbol);
        listWords.add(simbol, word);
        return simbol;
    }

    public int size(){
        return listWords.size();
    }
}
