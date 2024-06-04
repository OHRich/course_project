package org.example.tanchiki.services;

import java.io.*;
import java.net.Socket;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ServerTask {

    public static String loginOrSignUp(String operation, String username, String password){
        return sendRequest(operation + '/' + username + '/' + password);
    }

    public static void levelUp(String operation, String username, String password, int level){
        sendRequest(operation + '/' + username + '/' + password + '/' + level);
    }

    public static String sendRequest(String request) {
        try {
            Socket socket = new Socket("localhost", 12345);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(request);

            String response = in.readLine();

            in.close();
            out.close();
            socket.close();

            return response;
        } catch (IOException e) {
            showAlert("Ошибка", "Произошла ошибка при попытке подключения к серверу.");
            e.printStackTrace();
        }
        return "error_connect";
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
