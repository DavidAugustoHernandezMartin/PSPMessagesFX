package augusto.hernandez.messagesfx;

import augusto.hernandez.messagesfx.models.Message;
import augusto.hernandez.messagesfx.models.User;
import augusto.hernandez.messagesfx.models.responses.*;
import augusto.hernandez.messagesfx.utils.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private final DateTimeFormatter formatter = GsonService.formatter;

    private final ObservableList<User> userList = FXCollections.observableArrayList(new ArrayList<>());
    private final ObservableList<Message> messageList = FXCollections.observableArrayList(new ArrayList<>());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Configuración inicial
        // Configurar las columnas de la tabla de usuarios
        avatarColumn.setCellValueFactory(param -> {
            ImageView imageView = new ImageView(new Image(ServiceUtils.server + "/" + param.getValue().getImage(),true));
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            return new SimpleObjectProperty<>(imageView);
        });
        nicknameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableviewUsers.setItems(userList);
        // Configurar las columnas de la tabla de mensajes
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        messageImageColumn.setCellValueFactory(param -> {
            ImageView imageView = new ImageView(new Image(ServiceUtils.server + "/" + param.getValue().getImage(),true));
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
        //Cargar datos del servidor
        //Obtener el nombre de usuario
        labelUsername.setText(ServiceUtils.getUsername());
        //Obtener la imagen de perfil
        try{
            imageviewProfile.setImage(new Image(ServiceUtils.server +"/"+ServiceUtils.getImage(),true));
        }catch (NullPointerException e){
            MessageUtils.showError("Profile picture couldn't be loaded");
            imageviewProfile.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("img/ACapybaraPlaying.jpg"))));
        }
        //Cargar listado de mensajes y luego de usuarios
        loadMessages();
    }
    private void loadUsers(){
        try {
            NetworkService service = NetworkService.createService(ServiceUtils.server + "/users", null, "GET");
            service.setOnSucceeded((event)->{
                String result = service.getValue();
                UsersResponse response = GsonService.gson.fromJson(result, UsersResponse.class);
                if (response != null && response.ok) {
                    userList.setAll(response.getUsers() != null?response.getUsers(): new ArrayList<>());
                } else if (response != null) {
                    MessageUtils.showError("Registration failed:\n " + response.error);
                } else {
                    MessageUtils.showError("An error ocurred while fetching users data");
                }
            });
            service.setOnFailed((event -> MessageUtils.showError("A connection error ocurred while loading the users data: "+event.getSource().getException())));
            service.start();
        }catch (Exception e){
            MessageUtils.showError("Users data couldn't be loaded: "+e.getMessage());
        }
    }
    private void loadMessages(){
        try {
            NetworkService service = NetworkService.createService(ServiceUtils.server + "/messages", null, "GET");
            service.setOnSucceeded((event)->{
                String result = service.getValue();
                MessagesResponse response = GsonService.gson.fromJson(result, MessagesResponse.class);
                if (response != null && response.ok) {
                    messageList.setAll(response.getMessages() != null?response.getMessages(): new ArrayList<>());
                    loadUsers();
                } else if (response != null) {
                    MessageUtils.showError("Error while fetching received messages:\n " + response.error);
                } else {
                    MessageUtils.showError("Error while trying process the messages");
                }
            });
            service.setOnFailed((event -> MessageUtils.showError("A connection error ocurred while loading the users data: "+event.getSource().getException())));
            service.start();
        }catch (Exception e){
            MessageUtils.showError("Received messages data couldn't be loaded: "+e.getMessage());
        }
    }

    @FXML
    protected void selectMessageImage(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Image selectedImage = FileUtils.selectImage(stage);
        if(selectedImage != null){
            try {
                imageviewMessage.setOpacity(1);
                imageviewMessage.setImage(selectedImage);
            }catch (Exception e){
                MessageUtils.showError("Error while processing the picture: "+e.getMessage());
            }
        }
    }

    @FXML
    protected void sendMessage(ActionEvent actionEvent) {
        User user = tableviewUsers.getSelectionModel().getSelectedItem();
        if(!textfieldMessage.getText().isBlank() && user != null){
           try{
               JSONObject json =  new JSONObject();
               json.put("message", textfieldMessage.getText());
               String base64Image = ServiceUtils.imageToBase64(imageviewProfile.getImage());
               if(imageviewMessage.getOpacity() == 1 && !base64Image.isBlank()) {
                   json.put("image",base64Image);
               }
               json.put("sent",formatter.format(LocalDate.now()));
               String data = json.toString();
               NetworkService service = NetworkService.createService(ServiceUtils.server+"/user/"+user.getId(),data,"POST");
               service.setOnSucceeded((event)->{
                   String result = service.getValue();
                   SendMessageResponse response = GsonService.gson.fromJson(result,SendMessageResponse.class);
                   if(response != null && response.ok) {
                       MessageUtils.showMessage("Message sent!");
                       loadMessages();
                   } else if(response != null) {
                       MessageUtils.showError("Error while sending the message:\n "+response.error);
                   }else {
                       MessageUtils.showError("Connection to server failed.");
                   }
               });
               service.setOnFailed((event)-> {
                   MessageUtils.showError("Failure during session login aptempt.");
               });
               service.start();
           }catch(Exception e){
               MessageUtils.showMessage("Message couldn't be sent due the following error: "+e.getMessage());
           }
        }else{
            MessageUtils.showMessage("You must fill the text for the message and select a user before sending it");
        }
    }

    @FXML
    protected void changeProfileImage(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Image selectedImage = FileUtils.selectImage(stage);
        if(selectedImage != null){
            try {
                imageviewProfile.setImage(selectedImage);
                JSONObject json =  new JSONObject();
                json.put("id", ServiceUtils.getId());
                String base64Image = ServiceUtils.imageToBase64(imageviewProfile.getImage());
                json.put("image", base64Image);
                String data = json.toString();
                NetworkService service = NetworkService.createService(ServiceUtils.server + "/users", data, "PUT");
                service.setOnSucceeded((event)->{
                    String result = service.getValue();
                    UpdateUserResponse response = GsonService.gson.fromJson(result, UpdateUserResponse.class);
                    if (response != null && response.ok) {
                        ServiceUtils.setImage(base64Image);
                        MessageUtils.showMessage("Profile picture updated.");
                        loadUsers();
                    } else if (response != null) {
                        MessageUtils.showError("Error while updating the profile picture:\n " + response.error);
                    } else {
                        MessageUtils.showError("Error while processing the picture");
                    }
                });
                service.setOnFailed((event -> MessageUtils.showError("A connection error ocurred while uploading the picture: "+event.getSource().getException())));
                service.start();
            }catch (Exception e){
                MessageUtils.showError("Error while processing the picture: "+e.getMessage());
            }

        }
    }

    @FXML
    protected void refresh(ActionEvent actionEvent) {
        //Recargar los listados de mensajes y de usuarios
        loadMessages();
    }

    @FXML
    protected void deleteMessage(ActionEvent actionEvent) {
        Message message = tableviewYourMessages.getSelectionModel().getSelectedItem();
        if(message != null){
            boolean convinced = MessageUtils.approvalRequest("Are you sure you want to delete the selected message?");
            if(convinced) {
                try {
                    NetworkService service = NetworkService.createService(ServiceUtils.server + "/messages/" + message.getId(), null, "DELETE");
                    service.setOnSucceeded((event) -> {
                        String result = service.getValue();
                        DeleteMessageResponse response = GsonService.gson.fromJson(result, DeleteMessageResponse.class);
                        if (response != null && response.ok) {
                            MessageUtils.showMessage("Message has been deleted.");
                            loadMessages();
                        } else if (response != null) {
                            MessageUtils.showError("Error while trying to delete message:\n " + response.error);
                        } else {
                            MessageUtils.showError("Error while processing the deletion request");
                        }
                    });
                    service.setOnFailed((event -> MessageUtils.showError("A connection error ocurred while processing the request: " + event.getSource().getException())));
                    service.start();
                } catch (Exception e) {
                    MessageUtils.showError("Error while processing the request: " + e.getMessage());
                }
            }
        }else {
            MessageUtils.showError("You must select a message before deleting it");
        }

    }
}
