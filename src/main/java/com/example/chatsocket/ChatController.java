package com.example.chatsocket;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatController {

    @FXML
    private TextArea textAreaMessages;

    @FXML
    private TextField textFieldMessage;

    @FXML
    private Button buttonSend;

    private ClienteSocket cliente;

    @FXML
    public void initialize() {
        cliente = new ClienteSocket();

        // Hilo para recibir mensajes del servidor
        new Thread(() -> {
            try {
                cliente.conectar("localhost", 8080);

                // Mensaje inicial en la UI
                Platform.runLater(() -> textAreaMessages.appendText("✅ Conectado al servidor\n"));

                String mensaje;
                while ((mensaje = cliente.recibirMensaje()) != null) {
                    String finalMensaje = mensaje;

                    // Mostrar mensaje en el TextArea
                    Platform.runLater(() -> textAreaMessages.appendText(finalMensaje + "\n"));
                }

            } catch (Exception e) {
                Platform.runLater(() -> textAreaMessages.appendText("❌ Error: " + e.getMessage() + "\n"));
            }
        }).start();

        // Enviar mensaje al servidor cuando se presiona el botón
        buttonSend.setOnAction(e -> {
            String mensaje = textFieldMessage.getText().trim();
            if (!mensaje.isEmpty()) {
                cliente.enviarMensaje(mensaje);
                textFieldMessage.clear();
            }
        });
    }
}
