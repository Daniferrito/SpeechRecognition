package com.dani.speechrecognition;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vocabulary {

    private int maxSize;
    private Map<String, Integer> mapWords;
    private List<String> listWords;

    public Vocabulary() {
        mapWords = new HashMap<>();
        listWords = new ArrayList<>();
        maxSize = 1000;
    }

    public Vocabulary(int maxSize) {
        mapWords = new HashMap<>(maxSize);
        listWords = new ArrayList<>(maxSize);
        this.maxSize = maxSize;
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

    public int wordToSimbolAdding(String word) {
        try {
            return mapWords.get(word);
        } catch (Exception e) {
            int simbol = listWords.size();
            mapWords.put(word, simbol);
            listWords.add(simbol, word);
            return simbol;
        }
    }

    public String simbolToWord(int simbol) {
        return listWords.get(simbol);
    }

    String simbolsToWords(int[] simbols) {
        String words = "";
        for (int i = 0; i < simbols.length; i++) {
            if (simbols[i]==-1){
                words += "<NULL> ";
            } else {
                words += listWords.get(simbols[i]) + " ";
            }
        }
        return words.trim();
    }

    public final static int MAX_PERCENT_EDIT_DISTANCE = 35;
       // 100*EditDistance(word,voc) / word.length < 35
       // Palabras 1..2 max EditDistance = 0
       // Palabras 3..5 max EditDistance = 1
       // Palabras 6..8 max EditDistance = 2
       // Palabras 9..11 max EditDistance = 3
       // Palabras 12..14 max EditDistance = 4

    public final static int DISTANCE_WHEN_HIGHER_MAX_EDIT = 100;

    Pair<Integer, int[]> wordsToSimbols(String phase) { //Devuelve la distancia de edición acumulada
        int distance = 0;
        String[] words = phase.split(" ");
        int[] simbols = new int[words.length];

        for (int i = 0; i < words.length; i++) {
            simbols[i] = wordToSimbol(words[i]);
            if (simbols[i] == -1) { //buscamos palbra más parecida
                int bestDistance = Integer.MAX_VALUE;
                int bestSimbol = -1;
                for (int j = 0; j < listWords.size(); j++) {
                    int d = editDistance(listWords.get(j), words[i]);
                    if (d < bestDistance) {
                        bestDistance = d;
                        bestSimbol = j;
                    }
                }
                if (100*bestDistance / words[i].length() <= MAX_PERCENT_EDIT_DISTANCE) {
                    simbols[i] = bestSimbol;
                    distance += bestDistance;
                } else {
                    distance += DISTANCE_WHEN_HIGHER_MAX_EDIT;
                }
            }
        }
        return new Pair(distance,simbols);
    }

    public static int min(int a, int b, int c) {
        if (a < b) {
            return Math.min(a, c);
        } else { // b<=a
            return Math.min(b, c);
        }
    }

    public static int editDistance(String target, String source) {
        int n = target.length()+1;
        int m = source.length()+1;
        int[][] distance = new int[n][m];
        for (int i = 0; i < n; i++) distance[i][0] = i;
        for (int j = 0; j < m; j++) distance[0][j] = j;
        for (int i = 1; i < n; i++)
            for (int j = 1; j < m; j++)
                distance[i][j] =
                        min(distance[i - 1][j] + 1,
                                distance[i - 1][j - 1] +
                                        (target.charAt(i-1) == source.charAt(j-1) ? 0 : 1),
                                distance[i][j - 1] + 1);
        return distance[n - 1][m - 1];
    }

    public int add(String word) {
        int simbol = size();
        mapWords.put(word, simbol);
        listWords.add(simbol, word);
        return simbol;
    }

    public void add(String... words) {
        for (String word : words) {
            add(word);
        }
    }

    public int size() {
        return listWords.size();
    }

    public int getMaxSize() {
        return maxSize;
    }

}
