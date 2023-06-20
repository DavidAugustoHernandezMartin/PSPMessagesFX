package augusto.hernandez.messagesfx;

import augusto.hernandez.messagesfx.utils.MessageUtils;
import augusto.hernandez.messagesfx.utils.ScreenLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Register {
    @FXML
    public TextField textfieldUserRegister;
    @FXML
    public PasswordField textfieldPasswordRegister;
    @FXML
    public PasswordField textfieldPasswordRepeatRegister;
    @FXML
    public Button buttonCancelRegistration;
    @FXML
    public Button buttonRegistration;
    @FXML
    public Button buttonSelectImageRegister;

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
}
