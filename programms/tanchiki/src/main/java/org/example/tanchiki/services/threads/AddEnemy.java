package org.example.tanchiki.services.threads;

import org.example.tanchiki.models.GameObjectHelper;
import org.example.tanchiki.services.GameData;
import org.example.tanchiki.services.RandGenerate;
import org.example.tanchiki.models.GameStatus;
import org.example.tanchiki.models.gameObjects.Flag;
import org.example.tanchiki.models.SceneObject;
import org.example.tanchiki.models.gameObjects.operatorGift.OperatorGift;
import org.example.tanchiki.models.gameObjects.tank.Tank;
import org.example.tanchiki.models.gameObjects.tank.TankSide;
import org.example.tanchiki.models.gameObjects.tank.TankType;
import org.example.tanchiki.models.gameObjects.wall.Wall;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.tanchiki.GlobalConstance.*;
import static org.example.tanchiki.GlobalConstance.scale;

public class AddEnemy extends Thread {
    private volatile boolean running = true;

    public AddEnemy() {
    }

    @Override
    public void run() {
        TankType[] tankTypes = {TankType.NormalEnemy, TankType.RandomEnemy, TankType.StrongEnemy};
        while (running) {
            try {
                sleep(1000); // wait for 1 second
                if (canAddEnemy()) {
                    Tank sceneObject = createEnemy(tankTypes);
                    addEnemyToGame(sceneObject);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopRunning() {
        running = false;
    }

    @NotNull
    private Tank createEnemy(TankType[] tankTypes) {
        Point point = findEmptyPoint();
        TankType tankType = tankTypes[RandGenerate.getINSTANCE().getRanBetween(0, tankTypes.length)];
        return new Tank(tankType, TankSide.Enemy, point.x, point.y, scale);
    }

    private static void addEnemyToGame(Tank sceneObject) {
        synchronized (GameData.getInstance().getSceneObjects()) {
            GameData.getInstance().getSceneObjects().add(sceneObject);
            synchronized (GameData.getInstance().getEnemyTank()) {
                GameData.getInstance().getEnemyTank().add(sceneObject);
            }
        }
        GameData.getInstance().minusEnemyNumber();
    }

    private static boolean canAddEnemy() {
        return GameData.getInstance().getEnemyTank().size() < 4
                && GameData.getInstance().getEnemyNumber() > 0
                && GameData.getInstance().getGameStatus() == GameStatus.Running;
    }

    private Point findEmptyPoint() {
        int[][] nowayMap = new int[mapSize][mapSize];
        List<SceneObject> copyOfSceneObjects = new ArrayList<>(GameData.getInstance().getSceneObjects());
        updateNoWayMap(nowayMap, copyOfSceneObjects);
        int x, y;
        do {
            x = RandGenerate.getINSTANCE().getRanEnemyLoc();
            y = 0;
        } while (nowayMap[y][x] == 1);
        x = (int) (MAP_FIRST_X + x * scale);
        y = (int) (MAP_FIRST_Y + y * scale);
        return new Point(x, y);
    }

    private void updateNoWayMap(int[][] nowayMap, List<SceneObject> sceneObjects) {
        for (SceneObject sceneObject : sceneObjects) {
            if (sceneObject instanceof Wall wall) {
                GameObjectHelper.applyWall(nowayMap, wall);
            } else if (sceneObject instanceof Tank) {
                GameObjectHelper.applyObjectToMap(nowayMap, sceneObject);
            } else if (sceneObject instanceof Flag) {
                GameObjectHelper.applyObjectToMap(nowayMap, sceneObject);
            } else if (sceneObject instanceof OperatorGift) {
                GameObjectHelper.applyObjectToMap(nowayMap, sceneObject);
            }
        }
    }
}