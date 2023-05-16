package com.example.backtracking;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Model {

    private static int highestQualification;
    private static List<Person> bestGroup;
//    private final List<Person> people = new ArrayList<>();
    private final ObservableList<Person> people = FXCollections.observableArrayList();
    private final ObservableList<Person> notFriends = FXCollections.observableArrayList();


    public Model() {

        people.add(new Person("jack", 3, "programmer"));
        people.add(new Person("joaco", 4, "programmer"));
        people.add(new Person("ferchu", 5, "programmer"));
        people.add(new Person("grunge", 1, "programmer"));
        people.add(new Person("teti", 2, "programmer"));

        notFriends.add(people.get(0));
        notFriends.add(people.get(1));

        backtrack(people, new ArrayList<>(), notFriends, 0);

        System.out.println("The best score: " + highestQualification + " has it this group: " + bestGroup);
    }

    private void backtrack(List<Person> people, List<Person> tempList, List<Person> notFriends, int index) {

        if (tempList.size() == 4) {
            if (isCompatibleGroup(tempList, notFriends)) {
                setHighestQualification(tempList);
            }
        } else {
            for (int i = index; i < people.size(); i++) {
                if (tempList.contains(people.get(i)))
                    continue;

                tempList.add(people.get(i));

                backtrack(people, tempList, notFriends, i + 1);

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
    private boolean isCompatibleGroup(List<Person> tempList, List<Person> notFriends) {
        int count = 0;
        for (Person notFriend : notFriends) {
            if (tempList.contains(notFriend)) {
                count++;
                if (count >= 2) {
                    return false;
                }
            }
        }
        return true;
    }

    public void addPerson(Person person) {
        people.add(person);
        System.out.println("Se agregó con éxito "+person);
    }

    public void addNotFriendlyPerson(Person personSelected) {
        notFriends.add(personSelected);
        System.out.println("Se agregó con éxito "+personSelected);
    }

    // is a javaFX method
    public ObservableList<Person> getPeopleList() {
        return people;
    }
    public ObservableList<Person> getNotFriendsList() {
        return notFriends;
    }

}
