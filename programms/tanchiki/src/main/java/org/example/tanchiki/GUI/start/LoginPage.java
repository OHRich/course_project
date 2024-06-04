package org.example.tanchiki.GUI.start;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import org.example.tanchiki.models.Player;
import org.example.tanchiki.services.GameData;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tanchiki.services.ServerTask;
import org.jetbrains.annotations.NotNull;

import static org.example.tanchiki.GlobalConstance.*;

public class LoginPage {
    public static void loginBoxButtons(VBox loginBox, TextField usernameField, PasswordField passwordField,
                                       Scene scene, Pane pane, Stage stage) {
        Button signUpConfirmButton = createButton("Зарегистрироваться", BUTTON_STYLE,
                e -> signUpEvent(usernameField, passwordField, scene, pane, stage));
        Button loginButton = createButton("Войти", BUTTON_STYLE, e -> loginEvent(usernameField,
                passwordField, scene, pane, stage));
        loginBox.getChildren().addAll(loginButton, signUpConfirmButton);
    }

    public static void signOutBoxButtons(VBox signOutBox, Scene scene, Pane pane, Stage stage) {
        Button signOutButton = LoginPage.createButton("Выйти", BUTTON_STYLE, e -> LoginPage.signOutEvent(scene, pane, stage));
        signOutBox.getChildren().add(signOutButton);
    }

    @NotNull
    public static Button createButton(String text, String style, EventHandler<ActionEvent> event) {
        Button button = new Button(text);
        button.setStyle(style);
        button.setOnAction(event);
        return button;
    }

    private static void loginEvent(TextField usernameField, PasswordField passwordField,
                                   Scene scene, Pane pane, Stage stage) {
        if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()){
            String response = ServerTask.loginOrSignUp("login", usernameField.getText(), passwordField.getText());
            if (response.startsWith("success")){
                String[] parts = response.split("/");
                GameData.getInstance().setCurrentPlayer(new Player(usernameField.getText(), passwordField.getText(),
                        Integer.parseInt(parts[1])));
                StartMenu.makeLevelsMenuScene(stage, pane, scene);
            } else if (response.equals("error")) {
                showAlert("Ошибка", "Не верно введён логин или пароль.");
            }
        }
    }

    private static void signUpEvent(TextField usernameField, PasswordField passwordField,
                                    Scene scene, Pane pane, Stage stage) {
        if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()){
            String response = ServerTask.loginOrSignUp("signup", usernameField.getText(), passwordField.getText());
            if (response.startsWith("success")){
                GameData.getInstance().setCurrentPlayer(new Player(usernameField.getText(), passwordField.getText()));
                StartMenu.makeLevelsMenuScene(stage, pane, scene);
            } else if (response.equals("error")) {
                showAlert("Ошибка", "Логин уже занят.");
            }
        }
    }

    public static void signOutEvent(Scene scene, Pane pane, Stage stage) {
        GameData.getInstance().setCurrentPlayer(null);
        StartMenu.makeMenuScene(stage, pane, scene);
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
