package org.example.demo.view.components.modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.demo.view.components.cards.User;
import org.example.demo.view.core.Component;
import org.example.demo.view.dto.modal.AddUserRequestDTO;
import org.example.demo.view.dto.modal.AddUserResponseDTO;
import org.example.demo.view.service.UserService;

import java.io.IOException;
import java.util.function.Consumer;

public class AddUser extends Component {
    private UserService user = new UserService();

    @FXML private VBox addUser;
    @FXML private TextField name;
    @FXML private TextField surname;

    @FXML private RadioButton role;

    @FXML private TextField email;
    @FXML private TextField password;

    private Consumer<User> callback;

    @FXML
    public void close(ActionEvent event) {
        Stage stage = (Stage) addUser.getScene()
                .getWindow();
        stage.close();
    }

    public void closeWindow() {
        Stage stage = (Stage) addUser.getScene()
                .getWindow();
        stage.close();
    }

    public void setOnUserCreated(Consumer<User> callback) {
        this.callback = callback;
    }

    @FXML
    public void handleAdd() throws IOException, InterruptedException {
        AddUserResponseDTO response = createUser();
        User person = setUser(response);

        if (callback != null)
            callback.accept(person);
        closeWindow();
    }

    public void getName(AddUserRequestDTO request) {
        String name = this.name
                .getText();
        String surname = this.surname
                .getText();

        request.setName(name);
        request.setSurname(surname);
    }

    public void getRole(AddUserRequestDTO request) {
        if(this.role.isSelected())
            request.setRole((String) this
                    .role.getUserData());
        else
            request.setRole("NORMALE");
    }

    public void getLogin(AddUserRequestDTO request) {
        String email = this.email
                .getText();
        String password = this.password
                .getText();

        request.setEmail(email);
        request.setPassword(password);
    }

    public AddUserRequestDTO get() {
        AddUserRequestDTO request = new
                AddUserRequestDTO();

        getName(request);
        getRole(request);
        getLogin(request);

        return request;
    }

    public void setUsername(User user,
                            AddUserResponseDTO response) {
        user.setUsernameBold(response.getName() +
                " " + response.getSurname());
        user.setUsernameRegular(response.getSurname());
    }

    public void setUseremail(User user,
                             AddUserResponseDTO response) {
        user.setEmail(response
                .getEmail());
    }

    public void setUserole(User user,
                           AddUserResponseDTO response) {
        if(response.getRole()
                .equals("AMMINISTRATORE"))
            user.setAdmin();
        else
            user.setUser();
    }

    public User setUser(AddUserResponseDTO response) {
        User user = new User();

        setUsername(user, response);
        setUserole(user, response);
        setUseremail(user, response);

        return user;
    }

    public AddUserResponseDTO createUser() throws IOException,
            InterruptedException {
        AddUserRequestDTO request = get();
        AddUserResponseDTO response = user
                .createUser(request);
        return response;
    }
}
