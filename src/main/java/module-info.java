module augusto.hernandez.messagesfx {

    requires javafx.fxml;
    requires javafx.controls;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens augusto.hernandez.messagesfx to javafx.fxml;
    exports augusto.hernandez.messagesfx;
}