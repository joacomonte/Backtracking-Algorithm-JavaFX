package com.example.backtracking;


public class Person {
    private final String name;
    private int qualification;
    private String role;

    public Person(String name, int qualification, String role) {
        this.name = name;
        this.qualification = qualification;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public int getQualification() {
        return qualification;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return name + " (Qualification: " + qualification + ", Role: " + role + ")";
    }

}
