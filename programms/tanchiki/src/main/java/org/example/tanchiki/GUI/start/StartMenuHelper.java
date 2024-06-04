package org.example.tanchiki.GUI.start;

import org.example.tanchiki.Game;
import org.example.tanchiki.GUI.game.GamePage;
import org.example.tanchiki.services.GameData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tanchiki.services.Level;

import java.util.List;

import static org.example.tanchiki.GlobalConstance.*;

public class StartMenuHelper {

    public static void makeLevelsButton(Stage stage, Pane pane, Scene scene, HBox bottomOfPage) {
        HBox levelsButtons = new HBox();
        VBox optionButtons = createOptionsButton();
        VBox levelsButtonsLeft = new VBox();
        VBox levelsButtonsRight = new VBox();
        levelsButtons.setAlignment(Pos.CENTER);
        levelsButtons.setPadding(new Insets(30));
        List<Level> levels = Game.getLevels();
        configureVbox(levelsButtonsLeft);
        configureVbox(levelsButtonsRight);
        for (int i = 0; i < levels.size(); i++) {
            createLevelButton(levels, i, stage, pane, scene, levelsButtonsLeft, levelsButtonsRight);
            if (levels.get(i).num() == GameData.getInstance().getCurrentPlayer().getLastStage()) {
                break;
            }
        }
        levelsButtons.setAlignment(Pos.CENTER_RIGHT);
        levelsButtons.getChildren().addAll(levelsButtonsLeft, levelsButtonsRight, optionButtons);
        bottomOfPage.getChildren().add(levelsButtons);
    }

    private static void createLevelButton(List<Level> levels, int i, Stage stage, Pane pane, Scene scene,
                                          VBox levelsButtonsLeft, VBox levelsButtonsRight) {
        Button levelButton = LoginPage.createButton("Уровень " + levels.get(i).num(), BUTTON_STYLE,
                e -> GamePage.startGame(levels.get(i).num(), stage, pane, scene, GameData.getInstance(), PLAYER_TANK_HEALTH));
        if (i < levels.size() / 2) {
            levelsButtonsLeft.getChildren().add(levelButton);
        } else {
            levelsButtonsRight.getChildren().add(levelButton);
        }
    }

    private static VBox createOptionsButton() {
        VBox optionButtons = new VBox();
        configureVbox(optionButtons);
        return optionButtons;
    }

    public static VBox createLoginBox(Scene scene, Pane pane, Stage stage) {
        VBox loginBox = new VBox();
        TextField usernameField = new TextField();
        usernameField.setPromptText("Имя пользователя");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Пароль");

        loginBox.getChildren().addAll(usernameField, passwordField);
        LoginPage.loginBoxButtons(loginBox, usernameField, passwordField, scene, pane, stage);
        configureVbox(loginBox);
        return loginBox;
    }

    public static VBox createSignOutBox(Scene scene, Pane pane, Stage stage) {
        VBox signOutBox = new VBox();
        LoginPage.signOutBoxButtons(signOutBox, scene, pane, stage);
        configureVbox(signOutBox);
        return signOutBox;
    }

    private static void configureVbox(VBox vBox) {
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));
    }
}
