package com.example.chatsocket;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
/**
 * ManejadorClienteMultihilo
 * ------------------------
 * Clase que implementa Runnable para manejar la comunicación con un
 * único cliente en un hilo separado. Este patrón permite que el servidor
 * acepte múltiples clientes y atienda cada uno concurrentemente.
 *
 * Responsabilidades:
 * - Leer mensajes enviados por el cliente a través del socket.
 * - Responder con un mensaje "ECHO" para cada línea recibida.
 * - Detectar la palabra especial "salir" para terminar la conexión.
 * - Gestionar correctamente los recursos (streams y socket).
 */
public class ManejadorClienteMultihilo implements Runnable {
    // Socket para comunicarse con el cliente asignado a este manejador
    private final Socket socket;
    // Identificador simple del cliente (usado solo para logs/amigabilidad)
    private final int numeroCliente;
    /**
     * Constructor
     * @param socket Socket ya conectado al cliente
     * @param numeroCliente Número identificador del cliente (para logs)
     */
    public ManejadorClienteMultihilo(Socket socket, int numeroCliente) {
        this.socket = socket;
        this.numeroCliente = numeroCliente;
    }
    /**
     * Punto de entrada del hilo: gestiona la comunicación con el cliente.
     *
     * Implementación clave:
     * - Usamos try-with-resources para asegurar el cierre de los streams
     *   (BufferedReader y PrintWriter). El socket se cierra en el finally
     *   porque cerrar los streams no siempre cierra el socket en todas las
     *   implementaciones o si ocurre una excepción antes de crear los streams.
     * - PrintWriter se crea con autoflush=true para que cada println se envíe
     *   inmediatamente sin necesidad de llamar a flush() explícitamente.
     */
    @Override
    public void run() {
        PrintWriter salida = null;

        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            salida = new PrintWriter(socket.getOutputStream(), true);
            salida.println("¡Bienvenido! Eres el cliente #" + numeroCliente);
            // Agregar cliente a la lista global
            EchoServerMultihilo.listaClientes.add(salida);


            for (String cada_mensaje : EchoServerMultihilo.historialMensajes) {
                salida.println(cada_mensaje);
            }

            // Mensaje de bienvenida


            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                System.out.println("[Usuario #" + numeroCliente + "] " + mensaje);

                // Reenviar a todos los clientes conectados
                for (PrintWriter cliente : EchoServerMultihilo.listaClientes) {
                    cliente.println("[Usuario #" + numeroCliente + "] " + mensaje);
                }


                // Guardar mensaje en historial
                EchoServerMultihilo.historialMensajes.add("[Usuario #" + numeroCliente + "] " + mensaje);

                // Si el cliente se desconecta
                if (mensaje.equalsIgnoreCase("salir")) {
                    for (PrintWriter cliente : EchoServerMultihilo.listaClientes) {
                        cliente.println("👋 Usuario #" + numeroCliente + " se ha desconectado");
                    }
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Error con cliente #" + numeroCliente + ": " + e.getMessage());
        } finally {
            // Quitar cliente de la lista y cerrar socket
            if (salida != null) {
                EchoServerMultihilo.listaClientes.remove(salida);
            }
            try {
                socket.close();
                System.out.println("❌ Cliente #" + numeroCliente + " desconectado");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
