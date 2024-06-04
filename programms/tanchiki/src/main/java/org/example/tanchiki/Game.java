package org.example.tanchiki;

import org.example.tanchiki.GUI.SceneHelper;
import org.example.tanchiki.GUI.start.StartMenu;
import org.example.tanchiki.services.LevelService;
import org.example.tanchiki.services.threads.AddEnemy;
import org.example.tanchiki.services.threads.EnemyTankMovement;
import org.example.tanchiki.services.threads.GiveGift;
import org.example.tanchiki.services.Level;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;

public class Game extends Application {

    private static final Pane PANE = new Pane();
    private static final Scene SCENE = new Scene(PANE);

    private EnemyTankMovement enemyTankMover;
    private GiveGift giveGift;
    private AddEnemy addEnemy;
    private static List<Level> levels;

    public void start(Stage stage) {
        levels = LevelService.getLevels();
        SceneHelper.conformStage(stage, PANE, SCENE);
        startEnemyTankThread();
        startGiveGiftThread();
        startAddEnemyThread();
        StartMenu.makeMenuScene(stage, PANE, SCENE);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static List<Level> getLevels() {
        return levels;
    }

    public void startEnemyTankThread() {
        enemyTankMover = new EnemyTankMovement();
        enemyTankMover.start();
    }

    public void startGiveGiftThread() {
        giveGift = new GiveGift();
        giveGift.start();
    }

    public void startAddEnemyThread() {
        addEnemy = new AddEnemy();
        addEnemy.start();
    }

    @Override
    public void stop() {
        if (enemyTankMover != null) enemyTankMover.stopRunning();
        if (giveGift != null) giveGift.stopRunning();
        if (addEnemy != null) addEnemy.stopRunning();
        try {
            if (enemyTankMover != null) enemyTankMover.join();
            if (giveGift != null) giveGift.join();
            if (addEnemy != null) addEnemy.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
