package com.example.backtracking;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class View {
    private final Controller controller;
    private final VBox root;
    private final TextField nameField;
    private final ChoiceBox<Integer> numberChoiceBox;
    private final ChoiceBox<String> roleChoiceBox;
    private final Button addButton;

    public View(Controller controller) {
        this.controller = controller;

        nameField = createNameField();

        numberChoiceBox = createNumberChoiceBox();

        roleChoiceBox = createRoleChoiceBox();

        addButton = createAddButton();

        root = createRoot();
    }

    private TextField createNameField() {
        return new TextField();
    }

    private ChoiceBox<Integer> createNumberChoiceBox() {
        ObservableList<Integer> numberOptions = FXCollections.observableArrayList();
        for (int i = 1; i <= 10; i++) {
            numberOptions.add(i);
        }
        return new ChoiceBox<>(numberOptions);
    }

    private ChoiceBox<String> createRoleChoiceBox() {
        ObservableList<String> roleOptions = FXCollections.observableArrayList(
                "lider de proyecto",
                "arquitecto",
                "programador",
                "tester"
        );
        return new ChoiceBox<>(roleOptions);
    }

    private Button createAddButton() {
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            Integer number = numberChoiceBox.getValue();
            String role = roleChoiceBox.getValue();
            controller.addPerson(name, number, role);
            resetFields();
        });
        return addButton;
    }

    private VBox createRoot() {
        Label nameLabel = new Label("Name:");
        Label numberLabel = new Label("Calificación:");
        Label roleLabel = new Label("Role:");

        return new VBox(nameLabel, nameField, numberLabel, numberChoiceBox, roleLabel, roleChoiceBox, addButton);
    }

    public VBox getViewRoot() {
        return root;
    }

    public void resetFields() {
        nameField.clear();
        numberChoiceBox.getSelectionModel().clearSelection();
        roleChoiceBox.getSelectionModel().clearSelection();
    }
}
