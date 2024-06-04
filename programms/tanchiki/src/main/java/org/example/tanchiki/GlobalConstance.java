package org.example.tanchiki;

public class GlobalConstance {
    public final static int WINDOWS_HEIGHT = 600;

    public final static int WINDOWS_WIDTH = 800;

    public final static int MAP_FIRST_X = 50;

    public final static int MAP_FIRST_Y = 25;

    public static int mapHeight = 500;

    public static int mapSize = 20;

    public static double scale = (double) mapHeight / mapSize;

    public final static int NORMAL_TANK_HEALTH = 1;

    public final static int STRONG_TANK_HEALTH = 2;

    public final static int PLAYER_TANK_HEALTH = 3;

    public final static int DEFAULT_PLAYER_SHOT_DAMAGE = 1;

    public static int playerShotDamage = DEFAULT_PLAYER_SHOT_DAMAGE;

    public static final String DEFAULT_MAP = "src/main/resources/maps/map1,6.txt";

    public static String pathMap = "src/main/resources/maps/";

    public static final String BACKGROUND_COLOR = "-fx-background-color: #a83250;";
    public static final String FONT_FAMILY = "Verdana";
    public static final int FONT_SIZE = 50;
    public static final String GAME_TITLE = "Танчики";

    public static final String BUTTON_STYLE = "-fx-background-color: #808080; -fx-text-fill: white;";

    public static void updateSize() {
        if (mapHeight % mapSize != 0) {
            mapHeight = ((mapHeight / mapSize) + 1) * mapSize;
        }
        scale = (double) mapHeight / mapSize;
    }

    public static void resetPlayerShotDamage() {
        GlobalConstance.playerShotDamage = DEFAULT_PLAYER_SHOT_DAMAGE;
    }

    public static void applyPowerPlayerShotDamage() {
        playerShotDamage *= 2;
    }
}
