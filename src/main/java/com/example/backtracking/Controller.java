package com.example.backtracking;

import javafx.scene.control.Label;

public class Controller {
    private Model model;
    private Label counterLabel;

    public Controller(Model model) {
        this.model = model;
    }

    public void incrementCounter() {
        model.incrementCounter();
        updateCounterLabel();
    }

    public void resetCounter() {
        model.resetCounter();
        updateCounterLabel();
    }

    public int getCounter() {
        return model.getCounter();
    }

    public void setCounterLabel(Label counterLabel) {
        this.counterLabel = counterLabel;
        updateCounterLabel();
    }

    private void updateCounterLabel() {
        if (counterLabel != null) {
            counterLabel.setText("Counter: " + model.getCounter());
        }
    }
}
