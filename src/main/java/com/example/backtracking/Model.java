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

    //TODO delete this when done
    private int groupsFound = 0;


    public Model() {

        bestGroup = new ArrayList();
        //TODO DELETE THIS PERSONS ADD
        people.add(new Person("jack", 5, "Programmer"));
        people.add(new Person("joaco", 1, "Architect"));
        people.add(new Person("ferchu", 1, "Tester"));
        people.add(new Person("grunge", 1, "Project Leader"));
        people.add(new Person("teti", 5, "Programmer"));
        people.add(new Person("jack1", 3, "Programmer"));
//        people.add(new Person("jack1", 4, "Programmer"));
//        people.add(new Person("joaco2", 1, "Architect"));
//        people.add(new Person("ferchu3", 4, "Tester"));
//        people.add(new Person("grunge4", 4, "Project Leader"));
//        people.add(new Person("teti5", 3, "Programmer"));
//        people.add(new Person("jack8", 3, "Programmer"));
//        people.add(new Person("joaco8", 4, "Architect"));
//        people.add(new Person("ferchu8", 5, "Tester"));
//        people.add(new Person("grunge8", 1, "Project Leader"));
//        people.add(new Person("teti9", 2, "Programmer"));
//        people.add(new Person("jack19", 4, "Programmer"));
//        people.add(new Person("joaco29", 1, "Architect"));
//        people.add(new Person("ferchu39", 4, "Tester"));
//        people.add(new Person("grunge40", 4, "Project Leader"));
//        people.add(new Person("teti50", 3, "Programmer"));


        //TODO try to separate the backtracking into a thread
        //TODO add view for final team, and maybe add a counter of how many teams were formed


        //backtrack(people, new ArrayList<>(), incompatiblePairs, 0);

    }

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

    //TODO test addPerson
    public void addPerson(Person person) {
        people.add(person);
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


    public void updateTeamRequirements(int requiredProgrammersValue, int requiredArchitectsValue, int requiredProjectLeadersValue, int requiredTestersValue) {
        this.requiredProgrammers = requiredProgrammersValue;
        this.requiredArchitects = requiredArchitectsValue;
        this.requiredProjectLeaders = requiredProjectLeadersValue;
        this.requiredTesters = requiredTestersValue;
        this.totalTeamSize = requiredProgrammers + requiredArchitects + requiredProjectLeaders + requiredTesters;
    }

    public void runAlgorithm() {
        backtrack(people, new ArrayList<>(), incompatiblePairs, 0);
        //TODO Delete prints before turning over the essay
        System.out.println("The best score: " + highestQualification + " has it this group: " + bestGroup);
        System.out.println(groupsFound);
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
            //TODO DELETE THIS WHEN DONE
            groupsFound =0;
    }

    public int getGroupsFound() {
        return this.groupsFound;
    }
}
