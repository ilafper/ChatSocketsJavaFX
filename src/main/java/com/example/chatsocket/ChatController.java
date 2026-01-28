package com.example.chatsocket;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatController {
    // donde se mostraran los mensajes de todos
    @FXML
    private TextArea textAreaMessages;
    // input en donde escribes el mensaje
    @FXML
    private TextField textFieldMessage;
    // boton para la nueva ventana
    @FXML
     private Button nuevaVentana;
    // boton para enviar el mensaje
    @FXML
    private Button enviarBoton;
    // socket para el clinete
    private ClienteSocket cliente;

    @FXML
    private Button modalGrupo;




    @FXML
    public void initialize() {

        // crea nuevo socket por cada cliente
        cliente = new ClienteSocket();

        // Hilo para recibir mensajes del servidor
        new Thread(() -> {
            try {

                cliente.conectar("localhost", 8080);
                // Mensaje inicial en la interfaz, en el contenedor de mensajes

                Platform.runLater(() -> textAreaMessages.appendText("✅ Conectado al servidor\n"));


                String mensaje;

                //mientras el mensaje no sea null envia el mensaje
                while ((mensaje = cliente.recibirMensaje()) != null) {
                    String finalMensaje = mensaje;

                    // Mostrar mensaje en el TextArea
                    Platform.runLater(() -> textAreaMessages.appendText(finalMensaje + "\n"));
                }

            } catch (Exception error) {
                Platform.runLater(() -> textAreaMessages.appendText("❌ Error: " + error.getMessage() + "\n"));
            }

        }).start(); // inicia el hilo de cada cliente

        // Enviar mensaje
        enviarBoton.setOnAction(e -> {
            String mensaje = textFieldMessage.getText().trim();
            if (!mensaje.isEmpty()) {
                cliente.enviarMensaje(mensaje);
                textFieldMessage.clear();
            }
        });

        // boton de nueva ventana
        nuevaVentana.setOnAction(e->{
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 600, 500);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            stage.setTitle("Chat Cliente");
            stage.setScene(scene);
            stage.show();
        });

        modalGrupo.setOnAction(e->{
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("crear_unirse.fxml"));
            Scene scene = null;

            try {
                scene = new Scene(fxmlLoader.load(), 350, 200);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }


            stage.setTitle("modal");
            stage.setScene(scene);
            stage.show();
        });



    }
}
