package com.dani.speechrecognition;

/**
 * Created by Jesús Tomás on 04/03/2017.
 */

public class CarGrammar extends Grammar{

/*
      hood                  grid 1|2|3..8       completed
      roof                  <nada>              incompleted
      box                                       add sag
      tailgait                                  add mottle
      left door
      right door
      both door
      left fender
      right fender
      both fender
      alfa pillar
      bravo pillar
      charlie pillar
      */

    CarGrammar(){
        super(100); //100 = tamaño máximo de vocabulario
        newRule("<INI>","<PIECE>","hood","roof","box", "tailgait");
        newRule("<INI>","<LEFT_RIGHT>","left","right","both");
        newRule("<LEFT_RIGHT>","<PIECE>","door", "fender");
        newRule("<INI>","<PILLAR>","alfa","bravo","charlie");
        newRule("<PILLAR>","<PIECE>","pillar");

        newRule("<PIECE>","<GRID>","grid");
        newRule("<GRID>", "<NUM>", "one", "two", "three", "four", "five", "six", "seven", "eight");
        newRule("<PIECE>","<NUM>","");

        newRule("<NUM>","<END>","completed","incompleted");
        newRule("<NUM>","<ADD>","add");
        newRule("<ADD>","<END>","sag","mottle");
    }
}
