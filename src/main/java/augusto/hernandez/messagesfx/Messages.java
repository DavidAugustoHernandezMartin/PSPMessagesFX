package augusto.hernandez.messagesfx;

import augusto.hernandez.messagesfx.models.Message;
import augusto.hernandez.messagesfx.models.User;
import augusto.hernandez.messagesfx.utils.MessageUtils;
import augusto.hernandez.messagesfx.utils.ServiceUtils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class Messages implements Initializable {

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
    private TableView<Message> tableviewYourMessages;
    @FXML
    private TableColumn<Message, String> messageColumn;
    @FXML
    private TableColumn<Message, ImageView> messageImageColumn;
    @FXML
    private TableColumn<Message, LocalDate> sentColumn;
    @FXML
    protected Button buttonSendMessage;
    @FXML
    protected Button buttonSelectMessageImage;
    @FXML
    protected ImageView imageviewMessage;
    @FXML
    protected TextArea textfieldMessage;
    @FXML
    private TableView<User> tableviewUsers;
    @FXML
    private TableColumn<User, ImageView> avatarColumn;
    @FXML
    private TableColumn<User, String> nicknameColumn;

    // Definir el formato de las fechas
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final ObservableList<User> userList = FXCollections.observableArrayList();
    private final ObservableList<Message> messageList = FXCollections.observableArrayList();

    private FXCollections users;
    private FXCollections messages;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelUsername.setText(ServiceUtils.getUsername());

        // Configura las columnas de la tabla de usuarios
        avatarColumn.setCellValueFactory(param -> {
            ImageView imageView = new ImageView(new Image(param.getValue().getImage()));
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            return new SimpleObjectProperty<>(imageView);
        });
        nicknameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableviewUsers.setItems(userList);

        // Configura las columnas de la tabla de mensajes
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        messageImageColumn.setCellValueFactory(param -> {
            ImageView imageView = new ImageView(new Image(param.getValue().getImage()));
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            return new SimpleObjectProperty<>(imageView);
        });
        sentColumn.setCellValueFactory(new PropertyValueFactory<>("sent"));
        // Configurar el CellFactory de sentColumn para agregar el formato de fecha
        sentColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Message, LocalDate> call(TableColumn<Message, LocalDate> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(formatter.format(item));
                        }
                    }
                };
            }
        });
        tableviewYourMessages.setItems(messageList);

        try{
            imageviewProfile.setImage(new Image(ServiceUtils.server +"/"+ServiceUtils.getImage(),true));
        }catch (NullPointerException e){
            MessageUtils.showError("No se pudo cargar la imagen de perfil");
            imageviewProfile.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("img/ACapybaraPlaying.jpg"))));
        }
    }
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

    private void getMessages(){

    }
    private void getUsers(){

    }
    @FXML
    protected void deleteMessage(ActionEvent actionEvent) {
    }
}
