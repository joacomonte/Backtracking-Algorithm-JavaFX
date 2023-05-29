package com.example.backtracking;

import java.util.HashSet;
import java.util.Set;

public class Pairs {
    private Set<Person> pairSet;

    private Pairs(Set<Person> pairSet) {
        this.pairSet = pairSet;
    }

    public Set<Person> getPairSet() {
        return pairSet;
    }

    public static Pairs createPair(Person person1, Person person2) {
        Set<Person> pairSet = new HashSet<>();
        pairSet.add(person1);
        pairSet.add(person2);
        return new Pairs(pairSet);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Person person : pairSet) {
            sb.append(person.getName()).append(", ");
        }
        if (!pairSet.isEmpty()) {
            sb.setLength(sb.length() - 2); // Remove the last comma and space
        }
        sb.append("]");
        return sb.toString();
    }
}
