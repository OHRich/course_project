package org.example.tanchiki.services.eventHandler;

import org.example.tanchiki.services.GameData;
import org.example.tanchiki.models.gameObjects.Direction;
import org.example.tanchiki.models.gameObjects.tank.Tank;
import javafx.scene.input.KeyCode;

import static org.example.tanchiki.GlobalConstance.scale;

public class PlayerController {

    private static final PlayerController INSTANCE = new PlayerController();

    public static PlayerController getInstance() {
        return INSTANCE;
    }

    private PlayerController() {
    }

    public void handlePlayerMovements(KeyCode keyCode) {
        Tank player = GameData.getInstance().getPlayerTank();
        if (player != null) handlePlayerButtons(keyCode, player);
    }

    private void handlePlayerButtons(KeyCode keyCode, Tank player) {
        switch (keyCode) {
            case S:
                player.move(scale / 2, Direction.Down);
                break;
            case W:
                player.move(scale / 2, Direction.Up);
                break;
            case D:
                player.move(scale / 2, Direction.Right);
                break;
            case A:
                player.move(scale / 2, Direction.Left);
                break;
            case SPACE:
                player.fire();
                break;
            default:
                break;
        }
    }
}
