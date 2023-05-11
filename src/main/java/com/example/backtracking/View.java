package com.example.backtracking;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class View {
    private final Controller controller;
    private final VBox root;

    public View(Controller controller) {
        this.controller = controller;

        Label counterLabel = new Label("Counter: 0");
        Button incrementButton = createIncrementButton();
        Button resetButton = createResetButton();

        root = new VBox(counterLabel, incrementButton, resetButton);
    }

    public VBox getViewRoot() {
        return root;
    }

    private Button createIncrementButton() {
        Button button = new Button("Increment");
        button.setOnAction(event -> controller.incrementCounter());
        return button;
    }

    private Button createResetButton() {
        Button button = new Button("Reset");
        button.setOnAction(event -> controller.resetCounter());
        return button;
    }
}
