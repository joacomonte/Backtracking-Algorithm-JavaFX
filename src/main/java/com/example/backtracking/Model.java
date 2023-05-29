package com.example.backtracking;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class Model {

    private static int highestQualification;
    private static List<Person> bestGroup;
    private ObservableList<Person> people = FXCollections.observableArrayList();
    private ObservableList<Pairs> incompatiblePairs = FXCollections.observableArrayList();

    private int requiredProgrammers;
    private int requiredArchitects;
    private int requiredProjectLeaders;
    private int requiredTesters;

    private int totalTeamSize = requiredProgrammers + requiredArchitects + requiredProjectLeaders + requiredTesters;




    public Model() {

        people.add(new Person("jack", 3, "Programmer"));
        people.add(new Person("joaco", 4, "Architect"));
        people.add(new Person("ferchu", 5, "Tester"));
        people.add(new Person("grunge", 1, "Project Leader"));
        people.add(new Person("teti", 2, "Programmer"));
        requiredProgrammers = 1;
        requiredArchitects = 1;
        requiredProjectLeaders = 1;
        requiredTesters = 1;

        //TODO Fix point ranks, to 1-5. una gilada
        //TODO visualize final team
        //TODO add the view for it

//        addPairSet(people.get(0), people.get(1)); // Add pair set for Jack and Joaco

        backtrack(people, new ArrayList<>(), incompatiblePairs, 0);

        System.out.println("The best score: " + highestQualification + " has it this group: " + bestGroup);
    }

    private void backtrack(List<Person> people, List<Person> tempList, ObservableList<Pairs> incompatiblePairs, int index) {

        if (tempList.size() == 4) {
            if (isCompatibleGroup(tempList, incompatiblePairs)) {
                setHighestQualification(tempList);
            }
        } else {
            for (int i = index; i < people.size(); i++) {
                if (tempList.contains(people.get(i)))
                    continue;

                tempList.add(people.get(i));

                backtrack(people, tempList, incompatiblePairs, i + 1);

                tempList.remove(tempList.size() - 1);
            }
        }
    }

    private int calculateQualification(List<Person> tempList) {
        int sum = 0;
        for (Person person : tempList) {
            sum += person.getQualification();
        }
        return sum;
    }

    private void setHighestQualification(List<Person> tempList) {
        int qualy = calculateQualification(tempList);
        if (qualy > highestQualification) {
            highestQualification = qualy;
            bestGroup = new ArrayList<>(tempList);
        }
    }

    //TODO fix this motherfucker
    private boolean isCompatibleGroup(List<Person> tempList, List<Pairs> incompatiblePairs) {
        for (Pairs pairs : incompatiblePairs) {
            if (tempList.containsAll(pairs.getPairSet())) {
                return false;
            }
        }
        if (!everyRolCover(tempList)){
            return false;
        }

        return true;
    }

    private boolean everyRolCover(List<Person> tempList) {
        int projectLeaders = 0;
        int architects = 0;
        int programmers = 0;
        int testers = 0;

        for (Person person : tempList){
            if (person.getRole().equals("Project Leader")){
                projectLeaders ++;
            }
            if (person.getRole().equals("Architect")){
                architects ++;
            }
            if (person.getRole().equals("Programmer")){
                programmers ++;
            }
            if (person.getRole().equals("Tester")){
                testers ++;
            }
        }

        if (projectLeaders!=requiredProjectLeaders || architects!=requiredArchitects || programmers!=requiredProgrammers || testers!=requiredTesters){
            System.out.println("No es compatible gato");
            return false;
        }

        return true;
    }

    public void addPerson(Person person) {
        people.add(person);
        System.out.println("Se agregó con éxito "+person);
    }

    void addPairSet(Pairs pairSelected) {
        incompatiblePairs.add(pairSelected);
    }


    // is a javaFX method
    public ObservableList<Person> getPeopleList() {
        return people;
    }

    public ObservableList<Pairs> getIncompatiblePairs() {
        return incompatiblePairs;
    }


}
