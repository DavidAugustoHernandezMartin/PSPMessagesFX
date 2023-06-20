package augusto.hernandez.messagesfx.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ScreenLoader {
    public static void loadScreen(String viewPath, Stage stage) throws IOException,NullPointerException {
        Parent view = FXMLLoader.load(Objects.requireNonNull(ScreenLoader.class.getResource(viewPath)));
        Scene view1Scene = new Scene(view);
        stage.hide();
        stage.setScene(view1Scene);
        stage.show();
    }
}
