module com.example.backtracking {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.backtracking to javafx.fxml;
    exports com.example.backtracking;
}