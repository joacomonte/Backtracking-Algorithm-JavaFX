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
    private final ListView<Person> notFriendsListView;
    private Label successLabel;
    private final ChoiceBox<Person> notFriendlyChoiceBox;

    public View(Controller controller) {
        this.controller = controller;

        nameField = createNameField();
        numberChoiceBox = createNumberChoiceBox();
        roleChoiceBox = createRoleChoiceBox();
        addButton = createAddButton();
        peopleListView = createPeopleListView();
        notFriendsListView = createNotFriendsListView();
        notFriendlyChoiceBox = createNotFriendsChoiceBox();
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

    private ListView<Person> createNotFriendsListView() {
        ListView<Person> listView = new ListView<>();
//        TODO fix
//        listView.setItems(controller.getNotFriendsList());
        listView.setPrefHeight(200);
        return listView;
    }

    private ChoiceBox<Person> createNotFriendsChoiceBox() {
        ChoiceBox<Person> choiceBox = new ChoiceBox<>();
        ObservableList<Person> peopleList = controller.getNotFriendsListFiltered();
        choiceBox.setItems(peopleList);

        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
//                TODO fix
//                controller.addNotFriendlyPerson(newValue);
                showSuccessMessage();
            }
        });

        return choiceBox;
    }

    private void showSuccessMessage() {
        successLabel.setVisible(true);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> successLabel.setVisible(false)));
        timeline.play();
    }

    private VBox createRoot() {
        Label nameLabel = new Label("Nombre de la persona:");
        Label numberLabel = new Label("Elige un puntaje:");
        Label roleLabel = new Label("Elige qué rol tendrá:");
        Label peopleListLabel = new Label("Lista de personas:");
        Label notFriendsListLabel = new Label("Lista de personas no compatibles:");

        VBox.setMargin(nameLabel, new Insets(5, 0, 0, 0));
        VBox.setMargin(numberLabel, new Insets(10, 0, 0, 0));
        VBox.setMargin(roleLabel, new Insets(10, 0, 0, 0));
        VBox.setMargin(peopleListLabel, new Insets(10, 0, 0, 0));
        VBox.setMargin(notFriendsListLabel, new Insets(10, 0, 0, 0));

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(nameLabel, nameField, numberLabel, numberChoiceBox, roleLabel, roleChoiceBox, addButton, peopleListLabel, peopleListView, notFriendsListLabel, notFriendsListView, notFriendlyChoiceBox, successLabel);
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
