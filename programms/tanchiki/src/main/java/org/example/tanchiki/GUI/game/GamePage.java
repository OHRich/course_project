package org.example.tanchiki.GUI.game;

import org.example.tanchiki.GUI.EndGameScene;
import org.example.tanchiki.GUI.SceneHelper;
import org.example.tanchiki.services.GameData;
import org.example.tanchiki.models.GameStatus;
import org.example.tanchiki.models.SceneObject;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GamePage {

    public static void startGame(int level, Stage stage, Pane pane, Scene scene, GameData gameData, int health) {
        conformationGame(level, stage, pane, scene, gameData, health);
        gameData.setEnemyFreezing(false);
        gameLoop(pane, gameData, stage, scene);
        gameData.setGameStatus(GameStatus.Running);
    }

    private static void conformationGame(int level, Stage stage, Pane pane, Scene scene,
                                         GameData gameData, int health) {
        SceneHelper.conformStage(stage, pane, scene);
        SceneHelper.makeGameScene(pane);
        gameData.resetGame(level);
        gameData.setGameStatus(GameStatus.Start);
        GameEnvironmentHelper.readMap(gameData.getMap(), gameData.getSceneObjects(), health);
    }

    private static void gameLoop(Pane pane, GameData gameData, Stage stage, Scene scene) {
        new AnimationTimer() {
            private long lastUpdate = 0;

            public void handle(long currentNanoTime) {
                update(gameData);
                if (currentNanoTime - lastUpdate >= 100_000) {
                    draw(gameData, pane);
                    if (gameData.getGameStatus() == GameStatus.Stop) {
                        this.stop();
                        endGameMassage(gameData, pane, stage, scene);
                    }

                    lastUpdate = currentNanoTime;
                }
            }
        }.start();
    }

    private static void endGameMassage(GameData gameData, Pane pane, Stage stage, Scene scene) {
        gameData.setEnemyFreezing(true);
        if (gameData.getEnemyNumber() == 0 && gameData.getEnemyTank().isEmpty()) {
            EndGameScene.makeEndGame(true, pane, stage, scene);
        } else {
            EndGameScene.makeEndGame(false, pane, stage, scene);
        }
    }

    private static void draw(GameData gameData, Pane pane) {
        pane.getChildren().clear();
        SceneHelper.makeGameScene(pane);
        List<SceneObject> copyOfSceneObjects = new ArrayList<>(gameData.getSceneObjects());
        for (SceneObject sceneObject : copyOfSceneObjects) {
            pane.getChildren().add(sceneObject.getNode());
        }
    }

    private static void update(GameData gameData) {
        List<SceneObject> copyOfSceneObjects = new ArrayList<>(gameData.getSceneObjects());
        for (SceneObject sceneObject : copyOfSceneObjects) {
            if (sceneObject.isVisible()) {
                sceneObject.update();
            } else {
                gameData.getSceneObjects().remove(sceneObject);
            }
        }
        checkEndGame(gameData);
    }

    private static void checkEndGame(GameData gameData) {
        if (gameData.getPlayerTank() == null ||
                !GameData.getInstance().getPlayersFlag().isVisible() ||
                (gameData.getEnemyTank().isEmpty()
                        && gameData.getEnemyNumber() == 0)) {
            gameData.setGameStatus(GameStatus.Stop);
        }
    }
}
