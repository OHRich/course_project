package org.example.tanchiki.GUI;

import org.example.tanchiki.models.gameObjects.tank.Tank;
import org.example.tanchiki.services.GameData;
import org.example.tanchiki.services.eventHandler.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import static org.example.tanchiki.GlobalConstance.*;

public class SceneHelper {

    public static void conformStage(Stage stage, Pane pane, Scene scene) {
        pane.setStyle("-fx-background-color: #708090;");
        EventHandler.getInstance().attachEventHandlers(scene);
        stage.setScene(scene);
        stage.setTitle(GAME_TITLE);
        stage.setHeight(WINDOWS_HEIGHT);
        stage.setWidth(WINDOWS_WIDTH);
        stage.setMaxHeight(WINDOWS_HEIGHT);
        stage.setMaxWidth(WINDOWS_WIDTH);
        stage.setMinHeight(WINDOWS_HEIGHT);
        stage.setMinWidth(WINDOWS_WIDTH);
    }

    public static void makeGameScene(Pane pane) {
        Rectangle square = new Rectangle(mapHeight, mapHeight);
        square.setFill(Color.BLACK);
        square.setStroke(Color.WHITE);
        pane.getChildren().add(square);
        square.setX(MAP_FIRST_X);
        square.setY(MAP_FIRST_Y);
        Text scoreTitle = new Text("Счёт: " + String.valueOf(GameData.getInstance().getScore()));
        scoreTitle.setFill(Color.WHITE);
        scoreTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        scoreTitle.setX(WINDOWS_WIDTH - 225);
        scoreTitle.setY(250);
        pane.getChildren().add(scoreTitle);
        healthData(pane);
        userData(pane);
        tankShotData(pane);
        stageTitle(pane);
    }

    private static void stageTitle(Pane pane) {
        Text level = new Text("Уровень " + String.valueOf(GameData.getInstance().getLevel()));
        level.setFill(Color.WHITE);
        level.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        level.setX(WINDOWS_WIDTH - 225);
        level.setY(50);
        pane.getChildren().addAll(level);
    }

    private static void userData(Pane pane) {
        if (GameData.getInstance().getCurrentPlayer() != null) {
            Text username = createUsernameText();
            pane.getChildren().addAll(username);
        }
    }

    @NotNull
    private static Text createUsernameText() {
        Text username = new Text(GameData.getInstance().getCurrentPlayer().getUsername());
        username.setFill(Color.WHITE);
        username.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        username.setX(WINDOWS_WIDTH - 225);
        username.setY(100);
        return username;
    }

    private static void healthData(Pane pane) {
        if (GameData.getInstance().getPlayerTank() != null){
            Tank tank = GameData.getInstance().getPlayerTank();
            Text healthTitle = createHealthTitle(tank);
            pane.getChildren().add(healthTitle);
        }
    }

    @NotNull
    private static Text createHealthTitle(Tank tank) {
        Text healthTitle = new Text("Жизни: " + String.valueOf(tank.getHealth()));
        healthTitle.setFill(Color.WHITE);
        healthTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        healthTitle.setX(WINDOWS_WIDTH - 225);
        healthTitle.setY(200);
        return healthTitle;
    }

    private static void tankShotData(Pane pane) {
        Text shotDamageTitle = shotDamageTitle();
        pane.getChildren().add(shotDamageTitle);
    }

    @NotNull
    private static Text shotDamageTitle() {
        Text healthTitle = new Text("Сила выстрела: " + String.valueOf(playerShotDamage));
        healthTitle.setFill(Color.WHITE);
        healthTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        healthTitle.setX(WINDOWS_WIDTH - 225);
        healthTitle.setY(150);
        return healthTitle;
    }
}
