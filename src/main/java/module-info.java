module com.example.assignment02_ {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.example.assignment02_ to javafx.fxml;
    opens com.example.assignment02_.model to com.google.gson;
    exports com.example.assignment02_;
    exports com.example.assignment02_.controller;
    opens com.example.assignment02_.controller to javafx.fxml;
}
