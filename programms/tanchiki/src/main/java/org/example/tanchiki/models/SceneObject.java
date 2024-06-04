package org.example.tanchiki.models;

import javafx.scene.Node;

public interface SceneObject {
    double getX();

    double getY();

    void update();

    Node getNode();

    boolean collidesWith(SceneObject object);

    boolean isVisible();
}
