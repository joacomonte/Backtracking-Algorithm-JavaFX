package com.example.backtracking;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class Model {

    private static int highestQualification;
    private static List<Person> bestGroup;
//    private final List<Person> people = new ArrayList<>();
    private ObservableList<Person> people = FXCollections.observableArrayList();
    private ObservableList<Pairs> incompatiblePairs = FXCollections.observableArrayList();


    public Model() {

        people.add(new Person("jack", 3, "programmer"));
        people.add(new Person("joaco", 4, "programmer"));
        people.add(new Person("ferchu", 5, "programmer"));
        people.add(new Person("grunge", 1, "programmer"));
        people.add(new Person("teti", 2, "programmer"));

        //TODO visualize final team
        //TODO Fix requirements for the team
        //TODO cant visualize incompatibilities
        addPairSet(0, 1); // Add pair set for Jack and Joaco

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
        return true;
    }

    public void addPerson(Person person) {
        people.add(person);
        System.out.println("Se agregó con éxito "+person);
    }

    void addPairSet(int index1, int index2) {
        Person person1 = people.get(index1);
        Person person2 = people.get(index2);
        Set<Person> pairSet = new HashSet<>();
        pairSet.add(person1);
        pairSet.add(person2);
        incompatiblePairs.add(new Pairs(pairSet));
    }

    // is a javaFX method
    public ObservableList<Person> getPeopleList() {
        return people;
    }

    public ObservableList<Pairs> getIncompatiblePairs() {
        return incompatiblePairs;
    }


}
