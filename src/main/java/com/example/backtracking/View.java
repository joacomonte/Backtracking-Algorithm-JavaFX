package com.example.backtracking;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class View {
    private final Controller controller;
    private final VBox root;
    private final TextField nameField;
    private final ChoiceBox<Integer> numberChoiceBox;
    private final ChoiceBox<String> roleChoiceBox;
    private final Button addButton;
    private final ListView<Person> peopleListView;
    private Label successLabel;

    public View(Controller controller) {
        this.controller = controller;

        nameField = createNameField();
        numberChoiceBox = createNumberChoiceBox();
        roleChoiceBox = createRoleChoiceBox();
        addButton = createAddButton();
        peopleListView = createPeopleListView();
        successLabel = createSuccessLabel();

        root = createRoot();
    }
    private Label createSuccessLabel() {
        Label label = new Label("FELICITACIONES!! Se agregó con éxito!");
        label.setVisible(false);
        return label;
    }

    private TextField createNameField() {
        TextField nameField = new TextField();
        nameField.setPromptText("Enter name");
        return nameField;
    }

    private ChoiceBox<Integer> createNumberChoiceBox() {
        ObservableList<Integer> numberOptions = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        ChoiceBox<Integer> numberChoiceBox = new ChoiceBox<>(numberOptions);
        numberChoiceBox.setPrefWidth(150);
        numberChoiceBox.getSelectionModel().selectFirst(); // Select the first option by default
        return numberChoiceBox;
    }

    private ChoiceBox<String> createRoleChoiceBox() {
        ObservableList<String> roleOptions = FXCollections.observableArrayList(
                "Select role", // Default option
                "Project Leader",
                "Architect",
                "Programmer",
                "Tester"
        );
        ChoiceBox<String> roleChoiceBox = new ChoiceBox<>(roleOptions);
        roleChoiceBox.setPrefWidth(150);
        roleChoiceBox.getSelectionModel().selectFirst(); // Select the first option by default
        return roleChoiceBox;
    }

    private Button createAddButton() {
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            Integer number = numberChoiceBox.getValue();
            String role = roleChoiceBox.getValue();
            if (name.isEmpty() || number == null || role == null || role.equals("Select role")) {
                showAlert();
                return;
            }
            controller.addPerson(name, number, role);
            resetFields();
            showSuccessMessage();
        });
        return addButton;
    }

    private ListView<Person> createPeopleListView() {
        ListView<Person> listView = new ListView<>();
        listView.setItems(controller.getPeopleList());
        listView.setPrefHeight(200);
        return listView;
    }

    private void showSuccessMessage() {
        successLabel.setVisible(true);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> successLabel.setVisible(false)));
        timeline.play();
    }

    private VBox createRoot() {
        Label nameLabel = new Label("Name:");
        Label numberLabel = new Label("Calificación:");
        Label roleLabel = new Label("Role:");

        VBox.setMargin(nameLabel, new Insets(5, 0, 0, 0));
        VBox.setMargin(numberLabel, new Insets(10, 0, 0, 0));
        VBox.setMargin(roleLabel, new Insets(10, 0, 0, 0));

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(nameLabel, nameField, numberLabel, numberChoiceBox, roleLabel, roleChoiceBox, addButton, peopleListView, successLabel);
        return root;
    }

    public VBox getViewRoot() {
        return root;
    }

    public void resetFields() {
        nameField.clear();
        numberChoiceBox.getSelectionModel().clearSelection();
        roleChoiceBox.getSelectionModel().clearSelection();
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("Llene todo los campos");
        alert.setContentText("La persona debe tener nombre, clasificación y rol obligatoriamente");
        alert.showAndWait();
    }
}
