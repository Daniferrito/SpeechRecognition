package com.dani.speechrecognition;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesús Tomás on 17/02/2017.
 * Gramáticas deterministas ¿? (nolo podemos estar en un estado)
 * <p>
 * Formada por nodos y arcos, cada arco cosume una palabra
 * Regla: desde un nodo, consumiento una palabra pasamos a otro nodo
 * <INI> uno  <NUMERO>
 * <p>
 * Regla con multiples arcos
 * <INI> uno | dos | tres | cuatro <NUMERO>
 * <p>
 * Una gramática comienza por <INI> y acaba por <END>
 */

public class Grammar {

    class Rule {
        int arc[];         //  arco[simbol] = nodo_alcanzado
        int noSimbolNode;  // Podemos saltar a este nodo sin consumir símbolo

        Rule(Vocabulary vocabulary, int iEndNode, String... words) {
            noSimbolNode = NULL_NODE;  //Por defecto no hay  noSimbolNode
            arc = new int[vocabulary.getMaxSize()];
            for (String word : words) {
                if (word.equals("")) {
                    noSimbolNode = iEndNode;
                } else {
                    int simbol = vocabulary.wordToSimbolAdding(word);
                    arc[simbol] = iEndNode;
                }
            }

        }
    }

    class Node {
        String name;
        int indexNode;
        List<Rule> rules;

        Node(String name, int indexNode) {
            this.name = name;
            this.indexNode = indexNode;
            rules = new ArrayList<>();
        }
    }

    List<Node> nodes;       // Lista de nodos de la gramática
    Vocabulary vocSimbols;  // Palabras que admite
    Vocabulary vocNodes;    // Nombres de las estados

    protected static final String TAG = "Grammar";
    public static int NULL_NODE = 0;
    public static int INI_NODE = 1;
    public static int END_NODE = 2;

    public Grammar(int maxVocSize) {
        vocSimbols = new Vocabulary(maxVocSize);
        vocNodes = new Vocabulary("<NULL>", "<INI>", "<END>");
        nodes = new ArrayList<>(50);
        nodes.add(NULL_NODE, new Node("<NULL>", NULL_NODE)); // Un arco a <NULL> = no hay arco
        nodes.add(INI_NODE, new Node("<INI>", INI_NODE)); // Inicio de la gramática
        nodes.add(END_NODE, new Node("<END>", END_NODE)); // Fin de la gramática
    }

    Grammar(Vocabulary vocSimbols) {
        new Grammar(vocSimbols.getMaxSize());
        this.vocSimbols = vocSimbols;
    }

    public void newRule(String iniNode, String endNode, String... words) {
        int iIniNode = vocNodes.wordToSimbol(iniNode);
        if (iIniNode == -1) {
            Log.e("GRAMMAR", "Error en Regla: Simbolo " + iniNode + " no existe.");
            return;
        }
        int iEndNode = vocNodes.wordToSimbol(endNode);
        if (iEndNode == -1) {
            iEndNode = vocNodes.add(endNode);
            nodes.add(iEndNode, new Node(endNode, iEndNode));
        }
        Node node = nodes.get(iIniNode);
        node.rules.add(new Rule(vocSimbols, iEndNode, words));
    }

    public final static int DISTANCE_WHEN_NO_IN_GRAMMAR  = 1000;

    public int validSecuenceScore(String words) {
        Pair<Integer,int[]> p = validSecuence(words);
        return p.first;
    }

    public Pair<Integer,int[]> validSecuence(String words) {
        Pair<Integer,int[]> p = vocSimbols.wordsToSimbols(words) ;
        if (!validSecuence(p.second)) {
            p = new Pair<>(DISTANCE_WHEN_NO_IN_GRAMMAR,p.second);
        }
        return p;
    }

    public boolean validSecuence(int[] simbols) {
        return validSecuence(simbols, 0, 1);
    }

    public boolean validSecuence(int[] simbols, int i, int iNode) {
        boolean valid = false;
        Node node = nodes.get(iNode);
        for (Rule rule : node.rules) {
            if (simbols[i]==-1) return false;  //Contemplar inserción de símbolos con un peso
            int destNode = rule.arc[simbols[i]];
            if (destNode != NULL_NODE) {
                if (i == simbols.length - 1) {
                    if (destNode == END_NODE) {
                        return true;
                    }
                } else {
                    valid = valid || validSecuence(simbols, i + 1, destNode);
                }
            }
            if (rule.noSimbolNode != NULL_NODE){
                valid = valid || validSecuence(simbols, i, rule.noSimbolNode);
            }
        }
        return valid;
    }


    Pair<String,Integer> bestSecuenceAndScore(List<String> secuences){
        String bestOutput="";              // Si una palabra no esta en voc. la reemplaza por la más
        int bestScore = Integer.MAX_VALUE; // parecida. Selecciona la secuencia de menor distancia
        for (String s: secuences){         // de edición que esté en la gramática
            Pair<Integer,int[]> p = validSecuence(s);
            if (p.first < bestScore){
                bestOutput = vocSimbols.simbolsToWords(p.second);
                bestScore = p.first;
                if (p.first==0) return new Pair(bestOutput, 0);
            }
            Log.e(TAG, p.first+" "+s+" >> "+vocSimbols.simbolsToWords(p.second));
        }
        return new Pair(bestOutput, bestScore);
    }

    String bestSecuence(List<String> secuences){
        Pair<String,Integer> pair = bestSecuenceAndScore(secuences);
        return pair.first;
    }

}
