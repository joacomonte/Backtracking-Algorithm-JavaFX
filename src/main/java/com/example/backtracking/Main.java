package com.example.backtracking;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Model model = new Model();
        Controller controller = new Controller(model);
        View view = new View(controller);

        Scene scene = new Scene(view.getViewRoot(), 400, 1000);

        stage.setTitle("Counter App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
