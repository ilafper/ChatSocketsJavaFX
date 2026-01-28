package com.example.chatsocket;

import java.io.*;
import java.net.Socket;

//Clase de cada cliente en donde tendra un coket para cada3 uno el buffer de entrada y salida
public class ClienteSocket {
    //linea conexion con el servidor
    private Socket socket;
    //para escuchar al servidor,
    private BufferedReader entrada;
    //para mandar mensajes al servidor
    private PrintWriter salida;



    //funcion de conectar al servidor para cada cliente
    public void conectar(String host, int puerto) throws IOException {
        socket = new Socket(host, puerto);
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        salida = new PrintWriter(socket.getOutputStream(), true);

    }
    //funcion para enviar mensajes al servidor para cada cliente
    public void enviarMensaje(String mensaje) {
        if (salida != null) {
            salida.println(mensaje);
        }
    }
    //recibir los mensajes de los demas miembros
    public String recibirMensaje() throws IOException {
        if (entrada != null) {
            return entrada.readLine();
        }
        return null;
    }
}
