package com.daferto.common;

/**
 * Created by Dani on 07/03/2017.
 */

public class Pair <F, S> {
    public final F first;
    public final S second;

    public Pair(F first, S second) { this.first = first; this.second = second; }

    //public boolean equals(java.lang.Object o) { return false; }

    //public int hashCode() { return 0; }

    public java.lang.String toString() { return first.toString()+" "+second.toString(); }

    //public static <A, B> Pair<A,B> create(A a, B b) { return new Pair<A,B>(a,b); }
}
