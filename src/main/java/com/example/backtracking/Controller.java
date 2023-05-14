package com.example.backtracking;

public class Controller {
    private final Model model;

    public Controller(Model model) {
        this.model = model;
    }

    public void addPerson(String name, Integer number, String role) {
        Person person = new Person(name, number, role);
        model.addPerson(person);
    }
}