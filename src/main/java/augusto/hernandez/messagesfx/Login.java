package augusto.hernandez.messagesfx;


import augusto.hernandez.messagesfx.utils.MessageUtils;
import augusto.hernandez.messagesfx.utils.ScreenLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Login {

    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField textFieldUser;
    @FXML
    public Button buttonLogin;
    @FXML
    public Hyperlink linkRegistro;

    @FXML
    public void login(ActionEvent actionEvent) {
    }

    public void registro(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        try {
            ScreenLoader.loadScreen("/augusto/hernandez/messagesfx/register.fxml", stage);
        } catch (IOException e) {
            MessageUtils.showError("Ocurrió un error al intentar el registro: "+e.getMessage());
            System.err.println(e.getLocalizedMessage());
        } catch (NullPointerException n){
            MessageUtils.showError("Ocurrió un error el intentar cargar los datos: "+n.getMessage());
            n.printStackTrace();
        }
    }
}