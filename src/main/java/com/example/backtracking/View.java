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
    private final ListView<Pairs> notFriendsListView;
    private Label successLabel;
    private final ChoiceBox<Person> person1NotCompatibleChoiceBox;
    private final ChoiceBox<Person> person2NotCompatibleChoiceBox;
    private final Button addIncompatiblePairButton;

    public View(Controller controller) {
        this.controller = controller;

        nameField = createNameField();
        numberChoiceBox = createNumberChoiceBox();
        roleChoiceBox = createRoleChoiceBox();
        addButton = createAddButton();
        peopleListView = createPeopleListView();
        notFriendsListView = createNotFriendsListView();
        person1NotCompatibleChoiceBox = createNotFriendsChoiceBox1();
        person2NotCompatibleChoiceBox = createNotFriendsChoiceBox2();
        successLabel = createSuccessLabel();
        addIncompatiblePairButton = addIncompatiblePairButton(person1NotCompatibleChoiceBox, person2NotCompatibleChoiceBox);

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
        ObservableList<Integer> numberOptions = FXCollections.observableArrayList(1, 2, 3, 4, 5);
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
        Button addButton = new Button("Agregar");
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
        listView.setPrefHeight(400);
        return listView;
    }

    private ListView<Pairs> createNotFriendsListView() {
        ListView<Pairs> listView = new ListView<>();

        listView.setItems(controller.getIncompatiblePairs());
        listView.setPrefHeight(400);
        return listView;
    }

    private ChoiceBox<Person> createNotFriendsChoiceBox1() {
        ChoiceBox<Person> choiceBox = new ChoiceBox<>();
        ObservableList<Person> peopleList = controller.getPeopleList();
        choiceBox.setItems(peopleList);
        return choiceBox;
    }

    private ChoiceBox<Person> createNotFriendsChoiceBox2() {
        ChoiceBox<Person> choiceBox = new ChoiceBox<>();
        ObservableList<Person> peopleList = controller.getPeopleList();
        choiceBox.setItems(peopleList);
        return choiceBox;
    }

    private Button addIncompatiblePairButton(ChoiceBox<Person> choiceBox1, ChoiceBox<Person> choiceBox2) {
        Button button = new Button("Agregar");

        button.setOnAction(e -> {
            Person selectedPerson1 = choiceBox1.getSelectionModel().getSelectedItem();
            Person selectedPerson2 = choiceBox2.getSelectionModel().getSelectedItem();

            if (selectedPerson1 != null && selectedPerson2 != null) {
                controller.addIncompatiblePair(selectedPerson1, selectedPerson2);
                showSuccessMessage();
            }
        });

        return button;
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
        Label incompatiblePerson1 = new Label("Persona incompatible 1");
        Label incompatiblePerson2 = new Label("Persona incompatible 2");

        VBox.setMargin(nameLabel, new Insets(5, 0, 0, 0));
        VBox.setMargin(numberLabel, new Insets(10, 0, 0, 0));
        VBox.setMargin(roleLabel, new Insets(10, 0, 0, 0));
        VBox.setMargin(peopleListLabel, new Insets(10, 0, 0, 0));
        VBox.setMargin(notFriendsListLabel, new Insets(10, 0, 0, 0));
        VBox.setMargin(incompatiblePerson1, new Insets(10, 0, 0, 0));
        VBox.setMargin(incompatiblePerson2, new Insets(10, 0, 0, 0));

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(nameLabel, nameField, numberLabel, numberChoiceBox, roleLabel, roleChoiceBox, addButton, peopleListLabel, peopleListView, notFriendsListLabel, notFriendsListView, incompatiblePerson1, person1NotCompatibleChoiceBox, incompatiblePerson2, person2NotCompatibleChoiceBox, successLabel, addIncompatiblePairButton);
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
