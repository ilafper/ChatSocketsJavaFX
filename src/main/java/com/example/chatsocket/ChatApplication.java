package com.example.chatsocket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Abrimos la primera ventana al iniciar
        abrirNuevoCliente();

        // Creamos un Stage principal con botón para abrir nuevos clientes
        Stage controlStage = new Stage();
        AnchorPane root = new AnchorPane();
        Button nuevoClienteBtn = new Button("Nueva ventana");
        nuevoClienteBtn.setLayoutX(20);
        nuevoClienteBtn.setLayoutY(20);

        // Acción del botón: abrir otra ventana con chat
        nuevoClienteBtn.setOnAction(e -> {
            try {
                abrirNuevoCliente();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        root.getChildren().add(nuevoClienteBtn);
        Scene scene = new Scene(root, 300, 130);
        controlStage.setTitle("Control de Clientes");
        controlStage.setScene(scene);
        controlStage.show();
    }

    //Metodo que abre un cliente nuevo en una ventana independiente
    public void abrirNuevoCliente() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setTitle("Chat Cliente");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
