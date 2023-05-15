package com.example.backtracking;

import javafx.collections.ObservableList;

public class Controller {
    private final Model model;

    public Controller(Model model) {
        this.model = model;
    }

    public void addPerson(String name, Integer qualy, String role) {
        Person person = new Person(name, qualy, role);
        model.addPerson(person);
    }

    public void addNotFriendlyPerson(int index) {
        model.addNotFriendlyPerson(index);
        System.out.println(getPeopleList().get(index));

    }

    public ObservableList<Person> getPeopleList() {
        return model.getPeopleList();
    }

    public ObservableList<Person> getNotFriendsList() {
        return model.getNotFriendsList();
    }
}
