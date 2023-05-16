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

    public ObservableList<Person> getPeopleList() {
        return model.getPeopleList();
    }

    public ObservableList<Pairs> getIncompatiblePairs() {
        return model.getNotFriendsList();
    }

    public ObservableList<Person> getNotFriendsListFiltered() {
        return model.getPeopleList().filtered(person -> !model.getNotFriendsList().contains(person));
    }
}
