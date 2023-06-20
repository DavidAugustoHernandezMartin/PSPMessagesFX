package augusto.hernandez.messagesfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class Messages {
    @FXML
    protected ImageView imageviewProfile;
    @FXML
    protected Label labelUsername;
    @FXML
    protected Button buttonRefreshData;
    @FXML
    protected Button buttonDeleteMessage;
    @FXML
    protected Button buttonChangeProfileImage;
    @FXML
    protected TableView tableviewYourMessages;
    @FXML
    protected Button buttonSendMessage;
    @FXML
    protected Button buttonSelectMessageImage;
    @FXML
    protected ImageView imageviewMessage;
    @FXML
    protected TextArea textfieldMessage;
    @FXML
    protected TableView tableviewUsers;

    @FXML
    protected void selectMessageImage(ActionEvent actionEvent) {
    }

    @FXML
    protected void sendMessage(ActionEvent actionEvent) {
    }

    @FXML
    protected void changeProfileImage(ActionEvent actionEvent) {
    }

    @FXML
    protected void refresh(ActionEvent actionEvent) {
    }

    @FXML
    protected void deleteMessage(ActionEvent actionEvent) {
    }
}
