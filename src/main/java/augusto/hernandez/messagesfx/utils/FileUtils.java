package augusto.hernandez.messagesfx.utils;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;

public class FileUtils {

    // Crear el FileChooser
    private static final FileChooser fileChooser = new FileChooser();

    static {
        // Configurar el filtro de extensión del FileChooser para que solo se puedan seleccionar imágenes
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Imágenes", "*.jpg", "*.jpeg", "*.png", "*.bmp", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);
    }

    public static Image selectImage(Stage stage){
        File file = fileChooser.showOpenDialog(stage); // 'stage' es la ventana de tu aplicación
        if (file != null) {
            return new Image(file.toURI().toString(),true);
        }
        return null;
    }
}
