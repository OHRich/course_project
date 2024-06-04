package org.example.tanchiki.GUI.start;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import static org.example.tanchiki.GlobalConstance.*;

public class StartMenu {

    public static void makeMenuScene(Stage stage, Pane pane, Scene scene) {
        BorderPane root = createBorderPane();
        VBox page = new VBox();
        HBox topOfPage = createTitle();
        HBox bottomOfPage = new HBox();
        VBox loginBox = StartMenuHelper.createLoginBox(scene, pane, stage);
        bottomOfPage.getChildren().add(loginBox);
        page.getChildren().addAll(topOfPage, bottomOfPage);
        root.setCenter(page);
        stage.setScene(new Scene(root));
    }

    public static void makeLevelsMenuScene(Stage stage, Pane pane, Scene scene) {
        BorderPane root = createBorderPane();
        VBox page = new VBox();
        HBox topOfPage = createTitle();
        HBox bottomOfPage = new HBox();
        VBox signOutBox = StartMenuHelper.createSignOutBox(scene, pane, stage);
        bottomOfPage.getChildren().add(signOutBox);
        page.getChildren().addAll(topOfPage, bottomOfPage);
        root.setCenter(page);
        stage.setScene(new Scene(root));
        StartMenuHelper.makeLevelsButton(stage, pane, scene, bottomOfPage);
    }

    private static BorderPane createBorderPane() {
        BorderPane root = new BorderPane();
        root.setStyle(BACKGROUND_COLOR);
        return root;
    }

    private static HBox createTitle() {
        HBox titleBox = new HBox();
        Label title = new Label(GAME_TITLE);
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, FONT_SIZE));
        titleBox.getChildren().add(title);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(40));
        return titleBox;
    }
}