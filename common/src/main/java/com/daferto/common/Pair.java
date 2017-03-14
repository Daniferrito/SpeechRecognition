package com.daferto.common;


/**
 * Created by Dani on 07/03/2017.
 */

public class Pair<F, S> {
    public final F first;
    public final S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }


    /*public boolean equals(Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair<?, ?> p = (Pair<?, ?>) o;
        return Object.equal(p.first, first) && Object.equal(p.second, second);
    }*/


    public java.lang.String toString() {
        return first.toString() + " " + second.toString();
    }

    public int hashCode() {
        return (first == null ? 0 : first.hashCode()) ^ (second == null ? 0 : second.hashCode());
    }

    public static <A, B> Pair<A, B> create(A a, B b) {
        return new Pair<A, B>(a, b);

    }
}
