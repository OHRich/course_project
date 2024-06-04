package org.example.tanchiki.services.threads;

import org.example.tanchiki.models.GameStatus;
import org.example.tanchiki.models.gameObjects.Flag;
import org.example.tanchiki.services.GameData;
import org.example.tanchiki.services.RandGenerate;
import org.example.tanchiki.models.gameObjects.Direction;
import org.example.tanchiki.models.gameObjects.tank.Tank;

import java.util.ArrayList;

public class EnemyTankMovement extends Thread {
    private volatile boolean running = true;

    private static final int MOVE_INTERVAL = 100;
    private static final int FIRE_INTERVAL = 1500;

    public EnemyTankMovement() {
    }

    @Override
    public void run() {
        long lastMoveTime = System.currentTimeMillis();
        long lastFireTime = System.currentTimeMillis();

        while (running) {
            try {
                long currentTime = System.currentTimeMillis();

                if (!GameData.getInstance().isEnemyFreezing()) {
                    if (currentTime - lastMoveTime >= MOVE_INTERVAL) {
                        moveEnemyTanks();
                        lastMoveTime = currentTime;
                    }

                    if (currentTime - lastFireTime >= FIRE_INTERVAL) {
                        fireEnemyTanks();
                        lastFireTime = currentTime;
                    }
                } else if (GameData.getInstance().getGameStatus() == GameStatus.Running) {
                    sleep(5000);
                    GameData.getInstance().setEnemyFreezing(false);
                }

                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopRunning() {
        running = false;
    }

    private void moveEnemyTanks() {
        ArrayList<Tank> enemyTanks = new ArrayList<>(GameData.getInstance().getEnemyTank());
        for (Tank enemyTank : enemyTanks) {
            Direction[] directions = Direction.values();
            Direction direction = Direction.Up;
            if (GameData.getInstance().getPlayerTank() != null) {
                if (isPlayerNear(enemyTank)) {
                    direction = findDirection(GameData.getInstance().getPlayerTank(), enemyTank);
                } else {
                    direction = findDirectionFlag(GameData.getInstance().getPlayersFlag(), enemyTank);
                }
            }
            if (!enemyTank.move(5, direction)) {
                direction = directions[RandGenerate.getINSTANCE().getRanBetween(0, directions.length)];
                enemyTank.move(5, direction);
            }
        }
    }

    private void fireEnemyTanks() {
        ArrayList<Tank> enemyTanks = new ArrayList<>(GameData.getInstance().getEnemyTank());
        for (Tank enemyTank : enemyTanks) {
            enemyTank.fire();
        }
    }

    private Direction findDirection(Tank target, Tank self) {
        if (Math.abs(target.getX() - self.getX()) > Math.abs(target.getY() - self.getY())) {
            return (target.getX() > self.getX()) ? Direction.Right : Direction.Left;
        } else {
            return (target.getY() > self.getY()) ? Direction.Down : Direction.Up;
        }
    }

    private Direction findDirectionFlag(Flag target, Tank self) {
        if (Math.abs(target.getX() - self.getX()) > Math.abs(target.getY() - self.getY())) {
            return (target.getX() > self.getX()) ? Direction.Right : Direction.Left;
        } else {
            return (target.getY() > self.getY()) ? Direction.Down : Direction.Up;
        }
    }

    private boolean isPlayerNear(Tank self) {
        Tank target = GameData.getInstance().getPlayerTank();
        double distance1 = Math.pow(Math.abs(target.getX() - self.getX()), 2) +
                Math.pow(Math.abs(target.getY() - self.getY()), 2);
        Flag target2 = GameData.getInstance().getPlayersFlag();
        double distance2 = Math.pow(Math.abs(target2.getX() - self.getX()), 2) +
                Math.pow(Math.abs(target2.getY() - self.getY()), 2);

        return distance1 < distance2;
    }
}
