module augusto.hernandez.messagesfx {

    requires javafx.fxml;
    requires javafx.swing;
    requires javafx.base;
    requires javafx.controls;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.json;
    requires com.google.gson;
    requires java.desktop;

    opens augusto.hernandez.messagesfx to javafx.fxml;
    exports augusto.hernandez.messagesfx;
    opens augusto.hernandez.messagesfx.utils to javafx.fxml;
    exports augusto.hernandez.messagesfx.utils;
    opens augusto.hernandez.messagesfx.models.responses to com.google.gson;
    exports augusto.hernandez.messagesfx.models.responses;
}