package org.example.tanchiki.services.threads;

import org.example.tanchiki.models.GameObjectHelper;
import org.example.tanchiki.services.GameData;
import org.example.tanchiki.services.RandGenerate;
import org.example.tanchiki.models.gameObjects.Flag;
import org.example.tanchiki.models.SceneObject;
import org.example.tanchiki.models.gameObjects.operatorGift.GifType;
import org.example.tanchiki.models.gameObjects.operatorGift.OperatorGift;
import org.example.tanchiki.models.gameObjects.tank.Tank;
import org.example.tanchiki.models.gameObjects.wall.Wall;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.example.tanchiki.GlobalConstance.*;
import static org.example.tanchiki.GlobalConstance.scale;

public class GiveGift extends Thread {
    private volatile boolean running = true;

    public GiveGift() {
    }

    @Override
    public void run() {
        while (running) {
            try {
                sleep(1000); // wait for 1 second
                if (GameData.getInstance().sendGift) {
                    OperatorGift operatorGift = createOperatorGift();
                    GameData.getInstance().getSceneObjects().add(operatorGift);
                    GameData.getInstance().sendGift = false;
                    sleep(5000);
                    operatorGift.setExpired(true);
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
    private OperatorGift createOperatorGift() {
        Point point = findEmptyPoint();
        GifType[] gifTypes = GifType.values();
        GifType selected = gifTypes[new Random().nextInt(gifTypes.length)];
        OperatorGift operatorGift = new OperatorGift(point.x, point.y, scale, selected);
        return operatorGift;
    }

    private Point findEmptyPoint() {
        int[][] nowayMap = new int[mapSize][mapSize];
        List<SceneObject> copyOfSceneObjects = new ArrayList<>(GameData.getInstance().getSceneObjects());
        updateNoWayMap(nowayMap, copyOfSceneObjects);
        int x, y;
        do {
            x = RandGenerate.getINSTANCE().getRanBetween(0, mapSize);
            y = RandGenerate.getINSTANCE().getRanBetween(0, mapSize);
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