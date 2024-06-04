package org.example.tanchiki.models.gameObjects;

import org.example.tanchiki.models.GameObjectHelper;
import org.example.tanchiki.models.SceneObject;
import org.example.tanchiki.services.GameData;
import org.example.tanchiki.GlobalConstance;
import org.example.tanchiki.models.gameObjects.tank.Tank;
import org.example.tanchiki.models.gameObjects.tank.TankSide;
import org.example.tanchiki.models.gameObjects.wall.Wall;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Shot implements SceneObject {

    private final ImageView imageView;

    private final TankSide tankSide;

    private final int damage;

    private Direction direction;

    private double x;

    private double y;

    private final double scale;

    private boolean isCollision = false;

    public Shot(TankSide tankSide, double x, double y, double scale, int damage, Direction direction) {
        this.tankSide = tankSide;
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.damage = damage;
        this.direction = direction;
        this.imageView = new ImageView(GameObjectHelper.attachShotImage());
        imageView.setFitWidth(scale);
        imageView.setFitHeight(scale);
    }

    private void checkCollisions() {
        List<SceneObject> sceneObjects = new ArrayList<>(GameData.getInstance().getSceneObjects());
        for (SceneObject sceneObject : sceneObjects) {
            if (collidesWith(sceneObject)) {
                isCollision = true;
                break;
            }
        }
    }

    public void move(double speed, Direction direction) {
        this.direction = direction;
        switch (direction) {
            case Up -> y -= speed;
            case Down -> y += speed;
            case Right -> x += speed;
            case Left -> x -= speed;
        }
        checkCollisions();
    }

    public boolean collidesWith(SceneObject object) {
        if (object instanceof Tank tank) {
            return collisionWithTank(tank);
        } else if (object instanceof Wall wall) {
            return collisionWithWall(wall);
        } else if (object instanceof Flag flag) {
            return collisionWithFlag(flag);
        }
        return false;
    }

    private boolean collisionWithFlag(Flag flag) {
        if (tankSide == TankSide.Player) {
            return false;
        }
        return checkCollision(flag.getX(), flag.getY(), flag.getScale(), flag::takeDamage);
    }

    private boolean collisionWithTank(Tank tank) {
        if (tank.getTankSide() == tankSide) {
            return false;
        }
        boolean collision = checkCollision(tank.getX(), tank.getY(), tank.getScale(), tank::takeDamage);
        if (collision && tank.getTankSide() == TankSide.Player) {
            GlobalConstance.resetPlayerShotDamage();
        }
        return collision;
    }

    private boolean collisionWithWall(Wall wall) {
        return checkCollision(wall.getX(), wall.getY(), wall.getScale(), wall::takeDamage);
    }

    private boolean checkCollision(double objX, double objY, double objScale, Damageable damageable) {
        double shotLeft = x;
        double shotRight = x + scale;
        double shotTop = y;
        double shotBottom = y + scale;

        double objLeft = objX;
        double objRight = objX + objScale;
        double objTop = objY;
        double objBottom = objY + objScale;

        if (shotLeft < objRight && shotRight > objLeft && shotTop < objBottom && shotBottom > objTop) {
            damageable.takeDamage(damage);
            return true;
        }
        return false;
    }

    @Override
    public boolean isVisible() {
        return !isCollision;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void update() {
        move(5, direction);
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

    @FunctionalInterface
    private interface Damageable {
        void takeDamage(int damage);
    }
}
