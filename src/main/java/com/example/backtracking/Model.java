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

    private int totalTeamSize = 0;

    //counter for actual usable groups
    private int groupsFound = 0;


    public Model() {

        bestGroup = new ArrayList();

    }

    //main reason of the essay, backtracking algorithm
    private void backtrack(List<Person> people, List<Person> tempList, ObservableList<Pairs> incompatiblePairs, int index) {
        if (tempList.size() == totalTeamSize) {
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

    int calculateQualification(List<Person> tempList) {
        int sum = 0;
        for (Person person : tempList) {
            sum += person.getQualification();
        }
        return sum;
    }

    private void setHighestQualification(List<Person> tempList) {
        //groupsFound is a tracker for factible groups
        groupsFound ++;
        int qualy = calculateQualification(tempList);
        if (qualy > highestQualification) {
            highestQualification = qualy;
            bestGroup = new ArrayList<>(tempList);
        }
    }


    private boolean isCompatibleGroup(List<Person> tempList, List<Pairs> incompatiblePairs) {
        for (Pairs pairs : incompatiblePairs) {
            if (tempList.containsAll(pairs.getPairSet())) {
                return false;
            }
        }
        if (!everyRolCover(tempList)) {
            return false;
        }

        return true;
    }

    private boolean everyRolCover(List<Person> tempList) {
        int projectLeaders = 0;
        int architects = 0;
        int programmers = 0;
        int testers = 0;

        for (Person person : tempList) {
            if (person.getRole().equals("Project Leader")) {
                projectLeaders++;
            }
            if (person.getRole().equals("Architect")) {
                architects++;
            }
            if (person.getRole().equals("Programmer")) {
                programmers++;
            }
            if (person.getRole().equals("Tester")) {
                testers++;
            }
        }

        if (projectLeaders != requiredProjectLeaders || architects != requiredArchitects || programmers != requiredProgrammers || testers != requiredTesters) {
            System.out.println("El grupo no cumple con los minimos requeridos");
            return false;
        }

        return true;
    }


    public void addPerson(Person personToAdd) {
        for (Person person : people
             ) {
            if (personToAdd.getName().equals(person.getName()))
                return;
        }
        people.add(personToAdd);
    }

    void addPairSet(Pairs pairSelected) {
        incompatiblePairs.add(pairSelected);
    }



    public ObservableList<Person> getPeopleList() {
        return people;
    }

    public ObservableList<Pairs> getIncompatiblePairs() {
        return incompatiblePairs;
    }


    public void updateTeamRequirements(int requiredProgrammersValue, int requiredArchitectsValue, int requiredProjectLeadersValue, int requiredTestersValue) {
        this.requiredProgrammers = requiredProgrammersValue;
        this.requiredArchitects = requiredArchitectsValue;
        this.requiredProjectLeaders = requiredProjectLeadersValue;
        this.requiredTesters = requiredTestersValue;
        this.totalTeamSize = requiredProgrammers + requiredArchitects + requiredProjectLeaders + requiredTesters;
    }

    public void runAlgorithm() {
        backtrack(people, new ArrayList<>(), incompatiblePairs, 0);
    }

    public int getHighestQualification() {
        return highestQualification;
    }

    public List<Person> getFinalGroup() {
        return bestGroup;
    }

    public void resetBestGroup() {
       bestGroup.clear();
       highestQualification = 0;
       //reset groups counter
       groupsFound =0;
    }

    public int getGroupsFound() {
        return this.groupsFound;
    }
}
