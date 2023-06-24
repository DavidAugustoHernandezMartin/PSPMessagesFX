package augusto.hernandez.messagesfx;

import augusto.hernandez.messagesfx.models.responses.LoginResponse;
import augusto.hernandez.messagesfx.models.responses.RegisterResponse;
import augusto.hernandez.messagesfx.utils.*;
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
import org.json.JSONObject;

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
        try {
                if (!(textfieldPasswordRegister.getText().isBlank() ||
                        textfieldUserRegister.getText().isBlank() ||
                        textfieldPasswordRepeatRegister.getText().isBlank()) &&
                        (textfieldPasswordRepeatRegister.getText().equals(textfieldPasswordRegister.getText()))) {

                    JSONObject json = new JSONObject();
                    json.put("name", textfieldUserRegister.getText());
                    json.put("password", textfieldPasswordRegister.getText());
                    json.put("image",ServiceUtils.imageToBase64(imageviewRegister.getImage()));
                    String data = json.toString();
                    NetworkService service = NetworkService.createService(ServiceUtils.server + "/register", data, "POST");
                    disableFields(true);

                    service.setOnSucceeded((event) -> {
                        disableFields(false);
                        String result = service.getValue();
                        RegisterResponse response = GsonService.gson.fromJson(result, RegisterResponse.class);
                        if (response != null && response.ok) {
                            MessageUtils.showMessage("User registered: " + textfieldUserRegister.getText());
                        } else if (response != null) {
                            MessageUtils.showError("Registration failed:\n " + response.error);
                        } else {
                            MessageUtils.showError("An error ocurred while processing the registration");
                        }
                    });
                    service.setOnFailed((event) -> {
                        disableFields(false);
                    });
                    service.start();
                } else {
                    MessageUtils.showError("Registration form isn't complete or passwords don't match.");
                    disableFields(false);
                }
        }catch(Exception e){
            MessageUtils.showError("An error occurred during the registration process: " + e.getMessage());
        }
    }

    private void disableFields(boolean disable){
        buttonRegistration.setDisable(disable);
        buttonCancelRegistration.setDisable(disable);
        buttonSelectImageRegister.setDisable(disable);
    }
    @FXML
    protected void selectImageRegister(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageviewRegister.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("img/ACapybaraPlaying.jpg"))));
    }
}
