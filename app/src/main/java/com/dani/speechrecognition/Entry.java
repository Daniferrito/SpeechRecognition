package com.dani.speechrecognition;


public class Entry {

    String location;
    int state;
    String error;
    String prob;

    public Entry(){}

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getProb() {
        return prob;
    }

    public void setProb(String prob) {
        this.prob = prob;
    }

    @Override
    public String toString() {
        return location+" e:"+error+" s:"+state;
    }
}
