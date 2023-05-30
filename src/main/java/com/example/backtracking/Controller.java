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
        return model.getIncompatiblePairs();
    }

//    public ObservableList<Person> getNotFriendsListFiltered() {
//        return model.getPeopleList().filtered(person -> !model.getIncompatiblePairs().contains(person));
//    }

    public boolean addIncompatiblePair(Person selectedPerson1, Person selectedPerson2) {
        Pairs newPair = Pairs.createPair(selectedPerson1, selectedPerson2);

        if (selectedPerson1.equals(selectedPerson2)){
            return false;
        }

        for (Pairs existingPair : model.getIncompatiblePairs()) {
            if (existingPair.getPairSet().equals(newPair.getPairSet())) {
                return false;
            }
        }


        model.addPairSet(newPair);
        return true;
    }



    private int getIndexOfPerson(Person person) {
        ObservableList<Person> peopleList = model.getPeopleList();
        return peopleList.indexOf(person);
    }

}
