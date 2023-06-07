package com.example.backtracking;

import javafx.collections.ObservableList;

import java.util.List;

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


    //given 2 person, it add the pair to the incopatible pairs list
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


    public void runAlgorithm() {
      model.runAlgorithm();
    }

    public List<Person> getFinalGroup(){
        return model.getFinalGroup();
    }

    public void updateTeamRequirements(int requiredProgrammersValue, int requiredArchitectsValue, int requiredProjectLeadersValue, int requiredTestersValue) {
        model.updateTeamRequirements(requiredProgrammersValue, requiredArchitectsValue, requiredProjectLeadersValue, requiredTestersValue);
    }

    public int getFinalQualification() {
        return model.getHighestQualification();
    }

    public void resetBestGroup() {
        model.resetBestGroup();
    }

    public int groupsFound() {
        return model.getGroupsFound();
    }
}
