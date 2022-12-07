module com.example.lb1_javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires org.json;
    requires static lombok;
    requires java.persistence;
    requires java.sql;


    opens com.example.lb1_javafx to javafx.fxml;
    exports com.example.lb1_javafx;
    exports com.example.lb1_javafx.fxController;
    opens com.example.lb1_javafx.fxController to javafx.fxml;
    exports com.example.lb1_javafx.utils;
    opens com.example.lb1_javafx.utils to javafx.fxml;
    opens com.example.lb1_javafx.model to javafx.base;
    exports com.example.lb1_javafx.fxController.preview;
    opens com.example.lb1_javafx.fxController.preview to javafx.fxml;
    opens com.example.lb1_javafx.model.user to javafx.base;
    opens com.example.lb1_javafx.model.order to javafx.base;
    opens com.example.lb1_javafx.model.forum to javafx.base;
}