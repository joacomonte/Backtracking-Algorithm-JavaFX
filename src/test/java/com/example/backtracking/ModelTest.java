package com.example.backtracking;

import com.example.backtracking.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ModelTest {

    //create a list of person to use in multiple tests
    private Model model;
    private Controller controller;
    private View view;
    private List<Person> people;

    //create a model with 1 person for each roll at 1 qual everyone;
    @BeforeEach
    public void setup() {
        model = new Model();
//        controller = new Controller(model);
//        view = new View(controller);
        //people = new ArrayList<>();
        model.addPerson(new Person("jack", 1, "Programmer"));
        model.addPerson(new Person("joaco", 1, "Architect"));
        model.addPerson(new Person("ferchu", 1, "Tester"));
        model.addPerson(new Person("grunge", 1, "Project Leader"));

    }

    @Test
    public void addOnePersonTest(){
        model.addPerson(new Person("chano", 1, "Tester"));
        Assert.assertEquals(5, model.getPeopleList().size());
    }

    @Test
    public void resetBestGroupTest(){
        model.resetBestGroup();
        Assert.assertEquals(0, model.getFinalGroup().size());
        Assert.assertEquals(0, model.getHighestQualification());
    }


    @Test
    public void emptyListCalculateQualificationTest(){
        model.getPeopleList().clear();
        int teamQual = model.calculateQualification(model.getPeopleList());
        Assert.assertEquals(0, teamQual);
    }

    @Test
    public void calculateQualificationTest(){
        int teamQual = model.calculateQualification(model.getPeopleList());
        Assert.assertEquals(4, teamQual);
    }





}
