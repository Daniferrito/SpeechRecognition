package com.dani.speechrecognition;

/**
 * Created by Jesús Tomás on 04/03/2017.
 */

public class CarGrammar extends Grammar{

/*
      capó                  grid 1|2|3..8       completo
      techo                 <nada>              defecto
      box                                       añade bollo
      portón box                                añade mancha
      ambas aletas
      ambas puertas
      puerta derecha
      puerta izquierda
      aleta derecha
      aleta izquierda
      ambas puertas
      ambas aletas
      pilar a
      pilar b
      pilar c
      */

    CarGrammar(){
        super(100); //100 = tamaño máximo de vocabulario
        newRule("<INI>","<PIEZA>","capó","techo","box");
        newRule("<INI>","<PIEZA>","capó","techo","box");
        newRule("<INI>","<PUERTA>","puerta");
        newRule("<PUERTA>","<PIEZA>","derecha","izquierda");
        newRule("<INI>","<ALETA>","aleta");
        newRule("<ALETA>","<PIEZA>","derecha","izquierda");
        newRule("<INI>","<PORTÓN>","portón");
        newRule("<PORTÓN>","<PIEZA>","box");
        newRule("<INI>","<AMBAS>","ambas");
        newRule("<AMBAS>","<PIEZA>","puertas","aletas");
        newRule("<INI>","<PILAR>","pilar");
        newRule("<PILAR>","<PIEZA>","a","b","c");
        newRule("<PIEZA>","<GRID>","grid");
        newRule("<GRID>", "<NUM>", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho");
        newRule("<PIEZA>","<END>","completo","defecto","añade bollo","añade mancha");
        newRule("<NUM>","<END>","completo","defecto","añade bollo","añade mancha");
        newRule("<PIEZA>","<AÑADE>","añade");
        newRule("<NUM>","<AÑADE>","añade");
        newRule("<AÑADE>","<END>","bollo","mancha");
    }

}
