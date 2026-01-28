package com.example.chatsocket;

import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class EchoServerMultihilo {
    // puerto en el que se va a escuchar el servidor
    private static final int PUERTO = 8080;

    // Tamaño del pool de hilos - número máximo de clientes atendidos simultáneamente
    // Si hay más clientes, esperan en cola hasta que se libere un hilo

    private static final int MAX_CLIENTES = 10;

    // AtomicInteger: Variable thread-safe para contar clientes sin sincronización explícita
    private static final AtomicInteger clientesConectados = new AtomicInteger(0);

    // lista clientes que hay en el servidor conectados
    public static List<PrintWriter> listaClientes = new CopyOnWriteArrayList<>();

    //lista historial de mensajes,se hace una lista "segura" por que varios clientes en este caso hilos acceden al
    // a la misma lista y se modifica y eso puede dar error si usaramos una lista normal tipo Arraylist
    public static List<String> historialMensajes = new CopyOnWriteArrayList<>();

    public  static  List<String> gruposchat = new CopyOnWriteArrayList<>();



    public static void main(String[] args) {
        // ExecutorService: Framework de alto nivel para gestionar hilos
        // newFixedThreadPool(): Crea un pool con número fijo de hilos reutilizables
        // - Los hilos permanecen vivos esperando nuevas tareas
        // - Si todos están ocupados, las tareas esperan en una cola interna
        // - Más eficiente que crear un hilo nuevo por cada cliente

        //numero maximo de clientes, le añadimos la variable de numero de cientes maximos
        ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTES);

        // mensajes de bienvenida al iniciar el servidor
        System.out.println("Servidor multihilo iniciado en puerto " + PUERTO);
        System.out.println("Pool de hilos: " + MAX_CLIENTES);
        System.out.println("Servidor funcionando ");

        System.out.println(gruposchat);



        // try-with-resources: Garantiza cierre automático del ServerSocket al finalizar
        // ServerSocket: Socket pasivo que escucha conexiones entrantes en un puerto
        // - Vincula (bind) el puerto 8080 a esta aplicación
        // - Solo escucha, no establece conexiones por sí mismo
        // - Puede lanzar IOException si el puerto ya está en uso


        //crear el server socket al iniciar
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {

            // Bucle infinito: El servidor permanece activo aceptando nuevas conexiones
            // Se detendrá solo si ocurre una excepción o se interrumpe el proceso

            //se ejecutara siempre
            while (true) {
                // accept(): MEtodo BLOQUEANTE que espera una conexión entrante
                // - Detiene la ejecución hasta que un cliente se conecta
                // - Cuando un cliente se conecta, crea un nuevo Socket para comunicarse
                // - El ServerSocket sigue escuchando nuevas conexiones
                // - Retorna un Socket activo conectado al cliente específico


                //espera a clientes para que se conecten, al entrar le da un socket al cliente.
                Socket clienteSocket = serverSocket.accept();

                // incrementAndGet(): Operación atómica que incrementa y retorna el nuevo valor
                // Thread-safe: Múltiples hilos pueden llamarlo sin problemas de sincronización

                //incrementa el numero de cliente en funcion se van conectando
                int numCliente = clientesConectados.incrementAndGet();
                //mensaje de cliente conectado
                System.out.println("✅ Cliente #" + numCliente + " conectado: " + clienteSocket.getInetAddress());

                // execute(): Envía la tarea al pool de hilos
                // - Si hay un hilo libre, lo ejecuta inmediatamente
                // - Si todos están ocupados, la tarea espera en cola
                // - El hilo principal (main) no se bloquea, vuelve a accept() inmediatamente
                // - Esto permite aceptar nuevas conexiones mientras se atienden las existentes



                // ManejadorClienteMultihilo: Runnable que encapsula la lógica de atención al cliente

                pool.execute(new ManejadorClienteMultihilo(clienteSocket, numCliente));

            }

        } catch (IOException e) {
            // Captura errores de red o del socket
            System.err.println("Error en servidor: " + e.getMessage());
        } finally {
            // shutdown(): Inicia el apagado ordenado del pool
            // - No acepta nuevas tareas
            // - Los hilos activos finalizan sus tareas actuales
            // - Alternativa: shutdownNow() interrumpe inmediatamente todos los hilos
            pool.shutdown();
        }
    }

    /**
     * Runnable que maneja la comunicación con un cliente
     */

}