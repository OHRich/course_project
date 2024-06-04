package org.example.tanchiki.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.example.tanchiki.GlobalConstance.pathMap;

public class LevelService {

    public static List<Level> getLevels() {
        List<Level> levels = new ArrayList<>();
        File directory = new File(pathMap);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles((dir, name) -> name.startsWith("map"));

            if (files != null) {
                for (File file : files) {
                    String fileName = file.getName();
                    String[] parts = fileName.split("[,.]");
                    if (parts.length == 3 && parts[0].startsWith("map")) {
                        try {
                            int num = Integer.parseInt(parts[0].substring(3));
                            int enemies = Integer.parseInt(parts[1]);
                            Level level = new Level(fileName, num, enemies);
                            levels.add(level);
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            }
        }
        levels.sort(Comparator.comparingInt(Level::num));
        return levels;
    }
}
