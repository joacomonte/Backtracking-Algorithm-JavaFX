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

    public void addIncompatiblePair(Person selectedPerson1, Person selectedPerson2) {
        int indexPerson1 = getIndexOfPerson(selectedPerson1);
        int indexPerson2 = getIndexOfPerson(selectedPerson2);

        if (indexPerson1==indexPerson2){
            //view.errorForSamePerson();
            throw new RuntimeException("Debe elegir distintas personas");
        }
        else{
            model.addPairSet(indexPerson1, indexPerson2);
        }
    }

    private int getIndexOfPerson(Person person) {
        ObservableList<Person> peopleList = model.getPeopleList();
        return peopleList.indexOf(person);
    }

}
