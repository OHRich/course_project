package org.example.tanchiki.models;

import org.example.tanchiki.models.gameObjects.operatorGift.GifType;
import org.example.tanchiki.models.gameObjects.tank.TankType;
import org.example.tanchiki.models.gameObjects.wall.Wall;
import org.example.tanchiki.models.gameObjects.wall.WallType;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

import static org.example.tanchiki.GlobalConstance.*;

public class GameObjectHelper {

    private static Image playerTank;

    private static Image normalTank = null;

    private static Image randomTank = null;

    private static Image strongTank = null;

    private static Image normalWall = null;

    public static Image normalWallDamaged = null;

    private static Image ironWall = null;

    private static Image shot = null;

    public static Image flag1 = null;

    public static Image extraHealth = null;

    public static Image enemyFreezing = null;

    public static Image extraShot = null;

    static {
        try {
            playerTank = new Image(new FileInputStream("src/main/resources/images/tank-yellow/yellow-tank-up.gif"));
            normalTank = new Image(new FileInputStream("src/main/resources/images/tank-white/white-tank-up.gif"));
            strongTank = new Image(new FileInputStream("src/main/resources/images/tank-green/green-tank-up.gif"));
            randomTank = new Image(new FileInputStream("src/main/resources/images/tank-red/red-tank-up.gif"));
            normalWall = new Image(new FileInputStream("src/main/resources/images/wall.png"));
            normalWallDamaged = new Image(new FileInputStream("src/main/resources/images/wall-damaged.png"));
            ironWall = new Image(new FileInputStream("src/main/resources/images/wallIron.png"));
            shot = new Image(new FileInputStream("src/main/resources/images/shot/shot-up.gif"));
            flag1 = new Image(new FileInputStream("src/main/resources/images/home.gif"));
            extraHealth = new Image(new FileInputStream("src/main/resources/images/gift/giftTank.jpeg"));
            enemyFreezing = new Image(new FileInputStream("src/main/resources/images/gift/giftTime.png"));
            extraShot = new Image(new FileInputStream("src/main/resources/images/gift/giftStar.jpeg"));
        } catch (FileNotFoundException e) {
            System.out.println("Error get image " + e);
        }
    }

    public static Image attachTankImage(TankType tankType) {
        switch (tankType) {
            case Player -> {
                return playerTank;
            }
            case StrongEnemy -> {
                return strongTank;
            }
            case RandomEnemy -> {
                return randomTank;
            }
            default -> {
                return normalTank;
            }
        }
    }

    public static Image attachWallImage(WallType wallType) {
        if (Objects.requireNonNull(wallType) == WallType.Iron) {
            return ironWall;
        }
        return normalWall;
    }

    public static Image attachShotImage() {
        return shot;
    }

    public static Image attachGiftImage(GifType gifType) {
        switch (gifType) {
            case Enemy_Freezing -> {
                return enemyFreezing;
            }
            case Extra_Shot -> {
                return extraShot;
            }
            default -> {
                return extraHealth;
            }
        }
    }

    public static void applyObjectToMap(int[][] nowayMap, SceneObject object) {
        int j = (int) ((object.getX() - MAP_FIRST_X) / scale);
        int i = (int) ((object.getY() - MAP_FIRST_Y) / scale);
        nowayMap[i][j] = 1;
    }

    public static void applyWall(int[][] nowayMap, Wall wall) {
        if (wall.getX() > MAP_FIRST_X && wall.getX() < MAP_FIRST_X + mapHeight
                && wall.getY() > MAP_FIRST_Y && wall.getY() < MAP_FIRST_Y + mapHeight) {
            int j = (int) ((wall.getX() - MAP_FIRST_X) / scale);
            int i = (int) ((wall.getY() - MAP_FIRST_Y) / scale);
            nowayMap[i][j] = 1;
        }
    }
}
