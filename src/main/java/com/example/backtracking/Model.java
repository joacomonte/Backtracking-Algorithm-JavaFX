package com.example.backtracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Model {

    private static int highestQualification;
    private static List<Person> bestGroup;
    private final List<Person> people = new ArrayList<>();

    public Model() {

        people.add(new Person("jack", 3, "programmer"));
        people.add(new Person("joaco", 4, "programmer"));
        people.add(new Person("ferchu", 5, "programmer"));
        people.add(new Person("grunge", 1, "programmer"));
        people.add(new Person("teti", 2, "programmer"));

        List<Person> notFriends = new ArrayList<>();
        notFriends.add(people.get(0));
        notFriends.add(people.get(1));

        backtrack(people.toArray(new Person[0]), new ArrayList<>(), notFriends, 0);

        System.out.println("The best score: " + highestQualification + " has it this group: " + bestGroup);
    }

    private void backtrack(Person[] people, List<Person> tempList, List<Person> notFriends, int index) {
        if (tempList.size() == 4) {
            if (isCompatibleGroup(tempList, notFriends)) {
                setHighestQualification(tempList);
            }
        } else {
            for (int i = index; i < people.length; i++) {
                if (tempList.contains(people[i]))
                    continue;

                tempList.add(people[i]);

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
        System.out.println(people.get(0).getName());
    }
}
