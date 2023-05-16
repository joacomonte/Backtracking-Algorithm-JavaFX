package com.example.backtracking;

import java.util.Set;

public class Pairs {
    private Set<Person> pairSet;

    public Pairs(Set<Person> pairSet) {
        this.pairSet = pairSet;
    }

    public Set<Person> getPairSet() {
        return pairSet;
    }
}
