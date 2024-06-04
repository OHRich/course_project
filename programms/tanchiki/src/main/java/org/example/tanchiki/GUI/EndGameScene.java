package org.example.tanchiki.GUI;

import org.example.tanchiki.GUI.start.StartMenu;
import org.example.tanchiki.services.GameData;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static org.example.tanchiki.GlobalConstance.*;

public class EndGameScene {
    public static void makeEndGame(boolean win, Pane pane, Stage stage, Scene scene) {
        if (win) {
            createMessage(win, pane);
            updateLevel();
        } else {
            createMessage(win, pane);
        }
        new AnimationTimer() {
            int timer = 3;
            private long lastUpdate = 0;

            public void handle(long currentNanoTime) {
                if (currentNanoTime - lastUpdate >= 1_000_000_000) {
                    timer--;

                    if (timer == 0) {
                        this.stop();
                        GameData.getInstance().resetAll(0);
                        StartMenu.makeLevelsMenuScene(stage, pane, scene);
                    }
                    lastUpdate = currentNanoTime;
                }
            }
        }.start();
    }

    private static void createMessage(boolean win, Pane pane) {
        Text gameOver;
        if (win) {
            gameOver = new Text("Победа!");
            gameOver.setFill(Color.GREEN);
        } else {
            gameOver = new Text("Поражение!");
            gameOver.setFill(Color.RED);
        }
        gameOver.setFont(new Font(50));
        gameOver.setX(MAP_FIRST_X + (double) mapHeight / 2);
        gameOver.setY(MAP_FIRST_Y + (double) mapHeight / 2);
        pane.getChildren().add(gameOver);
    }

    private static void updateLevel() {
        if (GameData.getInstance().getLevel() == GameData.getInstance().getCurrentPlayer().getLastStage()) {
            GameData.getInstance().getCurrentPlayer().levelUp();
        }
    }
}
