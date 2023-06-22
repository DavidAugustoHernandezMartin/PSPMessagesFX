package augusto.hernandez.messagesfx;

import augusto.hernandez.messagesfx.utils.MessageUtils;
import augusto.hernandez.messagesfx.utils.ScreenLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Register implements Initializable {
    @FXML
    protected TextField textfieldUserRegister;
    @FXML
    protected PasswordField textfieldPasswordRegister;
    @FXML
    protected PasswordField textfieldPasswordRepeatRegister;
    @FXML
    protected Button buttonCancelRegistration;
    @FXML
    protected Button buttonRegistration;
    @FXML
    protected Button buttonSelectImageRegister;
    @FXML
    protected ImageView imageviewRegister;


    @FXML
    protected void cancelRegistration(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        try {
            ScreenLoader.loadScreen("/augusto/hernandez/messagesfx/login.fxml", stage);
        } catch (IOException e) {
            MessageUtils.showError("Ocurrió un error al cancelar el registro: "+e.getMessage());
            System.err.println(e.getLocalizedMessage());
        } catch (NullPointerException n){
            MessageUtils.showError("Ocurrió un error el intentar cargar datos: "+n.getMessage());
            n.printStackTrace();
        }
    }

    @FXML
    protected void submitRegistration(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        try {
            ScreenLoader.loadScreen("/augusto/hernandez/messagesfx/login.fxml", stage);
            MessageUtils.showMessage("Registro exitoso");
        } catch (IOException e) {
            MessageUtils.showError("Ocurrió un error al cancelar el registro: "+e.getMessage());
            System.err.println(e.getLocalizedMessage());
        } catch (NullPointerException n){
            MessageUtils.showError("Ocurrió un error el intentar cargar datos: "+n.getMessage());
            n.printStackTrace();
        }catch (Exception e){
            MessageUtils.showError("A ocurrido un error durante el proceso de registro");
        }
    }

    @FXML
    protected void selectImageRegister(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageviewRegister.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("img/ACapybaraPlaying.jpg"))));
    }
}
