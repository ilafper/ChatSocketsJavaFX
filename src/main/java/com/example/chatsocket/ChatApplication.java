package com.example.chatsocket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        nuevaVentanaUsuario();
        //modalUsuario();
    }

    public void nuevaVentanaUsuario() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setTitle("Chat Cliente");
        stage.setScene(scene);
        stage.show();
    }



//    public void modalUsuario() throws IOException {
//        Stage stage = new Stage();
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("modalNombre.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 300, 200);
//        stage.setTitle("Modal usuario");
//        stage.setScene(scene);
//        stage.show();
//    }


    public static void main(String[] args) {
        launch();
    }
}
