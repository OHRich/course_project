package org.example.tanchiki.models.gameObjects.tank;

import org.example.tanchiki.services.GameData;
import org.example.tanchiki.services.RandGenerate;
import org.example.tanchiki.models.gameObjects.Shot;
import org.example.tanchiki.models.gameObjects.Direction;
import org.example.tanchiki.models.GameObjectHelper;
import org.example.tanchiki.models.SceneObject;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

import static org.example.tanchiki.GlobalConstance.*;

public class Tank implements SceneObject {

    private final ImageView imageView;

    private final TankType tankType;

    private final TankSide tankSide;

    private int health;

    private int firstHealth;

    private Direction direction = Direction.Up;

    private double x;

    private double y;

    private final double scale;

    private long lastFireTime = 0;
    private static final int FIRE_INTERVAL = 300;

    private long lastMoveTime = 0;
    private static final int MOVE_INTERVAL = 100;

    public Tank(TankType tankType, TankSide tankSide, double x, double y, double scale) {
        imageView = new ImageView(GameObjectHelper.attachTankImage(tankType));
        this.scale = scale;
        imageView.setFitWidth(scale);
        imageView.setFitHeight(scale);
        this.tankType = tankType;
        this.tankSide = tankSide;
        this.x = x;
        this.y = y;
        switch (tankType) {
            case Player -> health = PLAYER_TANK_HEALTH;
            case NormalEnemy -> health = NORMAL_TANK_HEALTH;
            case StrongEnemy -> health = STRONG_TANK_HEALTH;
            default -> health = RandGenerate.getINSTANCE().getRanBetween(NORMAL_TANK_HEALTH, STRONG_TANK_HEALTH + 1);
        }
        firstHealth = health;
    }

    public double getScale() {
        return scale;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        firstHealth = health;
    }

    public boolean move(double speed, Direction direction) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastMoveTime < MOVE_INTERVAL) {
            return false;
        }
        lastMoveTime = currentTime;

        this.direction = direction;
        double oldY = y;
        double oldX = x;
        if (direction == Direction.Up) {
            y -= speed;
        }
        if (direction == Direction.Down) {
            y += speed;
        }
        if (direction == Direction.Right) {
            x += speed;
        }
        if (direction == Direction.Left) {
            x -= speed;
        }
        List<SceneObject> copyOfSceneObjects = new ArrayList<>(GameData.getInstance().getSceneObjects());
        for (SceneObject sceneObject : copyOfSceneObjects) {
            if (sceneObject.collidesWith(this) && this != sceneObject) {
                y = oldY;
                x = oldX;
                return false;
            }
        }
        return true;
    }

    public void fire() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFireTime >= FIRE_INTERVAL) {
            double shotX = x + scale / 3;
            double shotY = y + scale / 3;

            switch (direction) {
                case Up -> shotY -= scale / 3;
                case Down -> shotY += scale / 3;
                case Left -> shotX -= scale / 3;
                case Right -> shotX += scale / 3;
            }

            GameData.getInstance().getSceneObjects().add(new Shot(tankSide, shotX, shotY, scale / 3, playerShotDamage, direction));
            lastFireTime = currentTime;
        }
    }

    public boolean collidesWith(SceneObject object) {
        if (object == this) {
            return false;
        }
        if (object instanceof Tank) {
            return collisionWithTank(object);
        }
        return false;
    }

    public boolean collisionWithTank(SceneObject object) {
        Tank tank = (Tank) object;
        //if (tank.getTankSide() == tankSide) {
        //    return false;
        //}
        double tankScale = tank.getScale();
        double thisLeft = x;
        double thisRight = x + scale;
        double thisTop = y;
        double thisBottom = y + scale;
        double tankLeft = tank.getX();
        double tankRight = tank.getX() + tankScale;
        double tankTop = tank.getY();
        double tankBottom = tank.getY() + tankScale;
        return thisLeft < tankRight && thisRight > tankLeft && thisTop < tankBottom && thisBottom > tankTop;
    }

    @Override
    public boolean isVisible() {
        if (isDead()) {
            if (tankSide == TankSide.Player) {
                GameData.getInstance().setPlayerTank(null);
            } else {
                if (firstHealth == NORMAL_TANK_HEALTH) {
                    GameData.getInstance().setScore(GameData.getInstance().getScore() + 100);
                } else {
                    GameData.getInstance().setScore(GameData.getInstance().getScore() + 200);
                }
                if (tankType == TankType.RandomEnemy) {
                    GameData.getInstance().sendGift = true;
                }
                GameData.getInstance().getEnemyTank().remove(this);
            }
            return false;
        }
        return true;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public boolean isDead() {
        return (health <= 0);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public TankSide getTankSide() {
        return tankSide;
    }

    @Override
    public void update() {
        imageView.setX(x);
        imageView.setY(y);
        switch (direction) {
            case Down -> imageView.setRotate(180);
            case Right -> imageView.setRotate(90);
            case Left -> imageView.setRotate(270);
            default -> imageView.setRotate(0);
        }
    }

    @Override
    public Node getNode() {
        return imageView;
    }
}
