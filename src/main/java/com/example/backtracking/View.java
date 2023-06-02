package com.example.backtracking;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.scene.layout.HBox;

import java.util.List;

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
    private final Button runAlgorithm;

    private Spinner<Integer> requiredProgrammers;
    private Spinner<Integer> requiredArchitects;
    private Spinner<Integer> requiredProjectLeaders;
    private Spinner<Integer> requiredTesters;

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
        runAlgorithm = runAlgorithmButton();

        root = createRoot();
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
                fieldsAlert();
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

            if (controller.addIncompatiblePair(selectedPerson1, selectedPerson2)) {
                showSuccessMessage();
            } else{
                duplicatePairAlert();
            }
        });

        return button;
    }



    private Label createSuccessLabel() {
        Label label = new Label("FELICITACIONES!! Se agregó con éxito!");
        label.setVisible(false);
        return label;
    }

    private void showSuccessMessage() {
        successLabel.setVisible(true);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> successLabel.setVisible(false)));
        timeline.play();
    }


    private Button  runAlgorithmButton() {
        Button button = new Button("Buscar mejor equipo");

        button.setOnAction(e -> {
            updateSpinners();

            controller.runAlgorithm();
            List<Person> finalTeam = controller.getFinalGroup();
            int finalTeamQualification = controller.getFinalQualification();

            StringBuilder stringBuilder = new StringBuilder();
            for (Person person : finalTeam) {
                stringBuilder.append(person).append("\n");
            }

            String finalTeamToString = stringBuilder.toString();

            finalTeamAlert(finalTeamQualification, finalTeamToString);

        });

        return button;
    }

    //create the select boxes, labels and text areas. sets margins
    private VBox createRoot() {
        Label nameLabel = new Label("Nombre de la persona:");
        Label numberLabel = new Label("Elige un puntaje:");
        Label roleLabel = new Label("Elige qué rol tendrá:");
        Label peopleListLabel = new Label("Lista de personas:");
        Label notFriendsListLabel = new Label("Lista de personas no compatibles:");
        Label incompatiblePerson1 = new Label("Persona incompatible 1");
        Label incompatiblePerson2 = new Label("Persona incompatible 2");

        Label requiredProgrammersLabel = new Label("Seleccione la cantidad de Pogramadores deseados:");
        requiredProgrammers = createNumberSpinner();
        Label requiredArchitectsLabel = new Label("Seleccione la cantidad de Arquitectos deseados:");
        requiredArchitects = createNumberSpinner();
        Label requiredProjectLeadersLabel = new Label("Seleccione la cantidad de Project Leaders deseados:");
        requiredProjectLeaders = createNumberSpinner();
        Label requiredTestersLabel = new Label("Seleccione la cantidad de Testers deseados:");
        requiredTesters = createNumberSpinner();

        //set margins
        VBox.setMargin(nameLabel, new Insets(5, 0, 0, 0));
        VBox.setMargin(numberLabel, new Insets(10, 0, 0, 0));
        VBox.setMargin(roleLabel, new Insets(10, 0, 0, 0));
        VBox.setMargin(peopleListLabel, new Insets(10, 0, 0, 0));
        VBox.setMargin(notFriendsListLabel, new Insets(10, 0, 0, 0));
        VBox.setMargin(incompatiblePerson1, new Insets(10, 0, 0, 0));
        VBox.setMargin(incompatiblePerson2, new Insets(10, 0, 0, 0));

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(nameLabel, nameField, numberLabel, numberChoiceBox,
                roleLabel, roleChoiceBox, addButton, peopleListLabel, peopleListView,
                notFriendsListLabel, notFriendsListView, incompatiblePerson1,
                person1NotCompatibleChoiceBox, incompatiblePerson2, person2NotCompatibleChoiceBox,
                successLabel, addIncompatiblePairButton, requiredProgrammersLabel, requiredProgrammers,
                requiredArchitectsLabel, requiredArchitects, requiredProjectLeadersLabel, requiredProjectLeaders,
                requiredTestersLabel, requiredTesters, runAlgorithm);
        return root;
    }

    private Spinner<Integer> createNumberSpinner() {
        int minValue = 1;
        int maxValue = 100;
        int initialValue = 1;

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(minValue, maxValue, initialValue);

        Spinner<Integer> spinner = new Spinner<>();
        spinner.setValueFactory(valueFactory);

        TextField spinnerTextField = spinner.getEditor();

        spinner.setPrefWidth(80);

        // Add a value change listener to the spinner
        spinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                // Update the view or perform any other action
                updateSpinners();
            }
        });

        return spinner;
    }

    private void updateSpinners() {
        // Access the inputCountLabel and update its text
        int requiredProgrammersValue = requiredProgrammers.getValue();
        int requiredArchitectsValue = requiredArchitects.getValue();
        int requiredProjectLeadersValue = requiredProjectLeaders.getValue();
        int requiredTestersValue = requiredTesters.getValue();
        controller.updateTeamRequirements(requiredProgrammersValue, requiredArchitectsValue, requiredProjectLeadersValue, requiredTestersValue);
    }

    public VBox getViewRoot() {
        return root;
    }

    public void resetFields() {
        nameField.clear();
        numberChoiceBox.getSelectionModel().clearSelection();
        roleChoiceBox.getSelectionModel().clearSelection();
    }

    private void fieldsAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("Llene todo los campos");
        alert.setContentText("La persona debe tener nombre, clasificación y rol obligatoriamente");
        alert.showAndWait();
    }

    private void duplicatePairAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("Esa pareja ya existe");
        alert.setContentText("Las personas que estas queriendo ingresar ya corresponden a la lista de incompatibles");
        alert.showAndWait();
    }

    private void finalTeamAlert(int finalTeamScore, String team) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Mejor grupo encontrado!");
        alert.setHeaderText("El grupo encontrado tiene un score de: " + finalTeamScore + " y se compone por: ");
        alert.setContentText(team);
        alert.showAndWait();
    }
}
