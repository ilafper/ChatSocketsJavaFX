package com.example.chatsocket;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Grupos {
    private String Nombregrupo;

    //linea conexion con el servidor
    private Socket socket;
    //para escuchar al servidor,
    private BufferedReader entrada;
    //para mandar mensajes al servidor
    private PrintWriter salida;

}
