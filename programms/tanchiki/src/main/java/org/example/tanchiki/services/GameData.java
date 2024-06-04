package org.example.tanchiki.services;

import org.example.tanchiki.Game;
import org.example.tanchiki.GlobalConstance;
import org.example.tanchiki.models.GameStatus;
import org.example.tanchiki.models.Player;
import org.example.tanchiki.models.gameObjects.Flag;
import org.example.tanchiki.models.SceneObject;
import org.example.tanchiki.models.gameObjects.operatorGift.GifType;
import org.example.tanchiki.models.gameObjects.tank.Tank;
import org.example.tanchiki.GUI.game.GameEnvironmentHelper;

import java.util.ArrayList;
import java.util.List;

import static org.example.tanchiki.GlobalConstance.*;

public class GameData {

    private final static GameData INSTANCE = new GameData();

    private GameData() {

    }

    public static GameData getInstance() {
        return INSTANCE;
    }

    private List<SceneObject> sceneObjects = new ArrayList<>();

    private GameStatus gameStatus = GameStatus.Stop;

    private Tank playerTank = null;

    private Flag playersFlag = null;

    private ArrayList<Tank> enemyTank = new ArrayList<>();

    private String[][] map;

    private int score = 0;

    private boolean enemyFreezing = false;

    public boolean sendGift = false;

    private Player currentPlayer = null;

    private int level;

    private int enemyNumber = 4;

    public Tank getPlayerTank(){
        return playerTank;
    }

    public void setPlayerTank(Tank tank) {
        playerTank = tank;
    }

    public ArrayList<Tank> getEnemyTank() {
        return enemyTank;
    }

    public List<SceneObject> getSceneObjects() {
        return sceneObjects;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isEnemyFreezing() {
        return enemyFreezing;
    }

    public void setEnemyFreezing(boolean enemyFreezing) {
        this.enemyFreezing = enemyFreezing;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String[][] getMap() {
        return map;
    }

    public void resetGame(int level) {
        this.level = level;
        sceneObjects = new ArrayList<>();
        gameStatus = GameStatus.Running;
        playerTank = null;
        enemyTank = new ArrayList<>();
        score = 0;
        enemyFreezing = true;
        sendGift = false;
        applyLevel(level);
        map = GameEnvironmentHelper.readMapFile(pathMap + Game.getLevels().get(level - 1).name());
    }

    public void resetAll(int level) {
        sceneObjects = new ArrayList<>();
        gameStatus = GameStatus.Running;
        playerTank = null;
        enemyTank = new ArrayList<>();
        score = 0;
        enemyFreezing = true;
        sendGift = false;
        applyLevel(level);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getLevel() {
        return level;
    }

    public int getEnemyNumber() {
        return enemyNumber;
    }

    public void minusEnemyNumber() {
        enemyNumber--;
    }

    public void applyGift(GifType gifType) {
        switch (gifType) {
            case Enemy_Freezing -> {
                enemyFreezing = true;
            }
            case Extra_Shot -> {
                GlobalConstance.applyPowerPlayerShotDamage();
            }
            default -> {
                playerTank.setHealth(playerTank.getHealth() + 1);
            }
        }
    }

    private void applyLevel(int level) {
        if (level == 0) {
            enemyNumber = 0;
        } else {
            enemyNumber = Game.getLevels().get(level - 1).enemies();
        }
    }

    public Flag getPlayersFlag() {
        return playersFlag;
    }

    public void setPlayersFlag(Flag playersFlag) {
        this.playersFlag = playersFlag;
    }
}
