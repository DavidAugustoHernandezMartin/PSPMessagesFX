package augusto.hernandez.messagesfx;

import augusto.hernandez.messagesfx.models.responses.LoginResponse;
import augusto.hernandez.messagesfx.utils.*;
import org.json.JSONObject;
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
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        try {
            JSONObject json =  new JSONObject();
            json.put("name", textFieldUser.getText());
            json.put("password", passwordField.getText());
            String data = json.toString();
            NetworkService service = NetworkService.createService(ServiceUtils.server+"/login",data,"POST");
            buttonLogin.setDisable(true);
            service.setOnSucceeded((event)->{
                String result = service.getValue();
                LoginResponse response = GsonService.gson.fromJson(result,LoginResponse.class);
                if(response.ok) {
                    try {
                        ServiceUtils.setToken(response.getToken());
                        ServiceUtils.setUsername(response.getName());
                        ServiceUtils.setImage(response.getImage());
                        ScreenLoader.loadScreen("/augusto/hernandez/messagesfx/messages.fxml", stage);
                        MessageUtils.showMessage("datos de login obtenidos.\nToken: " + response.getToken());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    MessageUtils.showError("Falló de inicio de sesión:\n "+response.error);
                    buttonLogin.setDisable(false);
                }
            });
            service.setOnFailed((event)-> {
                MessageUtils.showError("Falló la aplicación al intentar el inicio de sesión.");
                buttonLogin.setDisable(false);
            });
            service.start();
        } catch (Exception e) {
            ServiceUtils.removeToken();
            ServiceUtils.removeUsername();
            MessageUtils.showError("Ocurrió un error al intentar iniciar la aplicación: "+e.getMessage());
            System.err.println(e.getLocalizedMessage());
        }
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