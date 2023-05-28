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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Incompatibles: [");
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
