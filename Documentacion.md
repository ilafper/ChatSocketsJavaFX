# ChatSocket - Aplicación de Chat Grupal en Java

## Introducción

Para esta práctica crearemos un chat grupal con sockets TCP en donde cada cliente ve 
todos los mensajes de todos los usuarios.

---

## Objetivo

El objetivo principal de **ChatSocket** es:

* Permitir la comunicación simultánea entre múltiples usuarios en tiempo real.
* Gestionar conexiones concurrentes de clientes mediante un servidor multihilo.
* Mostrar un historial de mensajes a cada nuevo cliente que se conecta.
* Proporcionar una interfaz intuitiva para enviar y recibir mensajes.

---

## Flujo de la Aplicación

1. **Inicio del Servidor:**

    * Se ejecuta `EchoServerMultihilo`.
    * El servidor escucha conexiones entrantes en el puerto `8080`.
    * Se gestiona un pool de hilos (`ExecutorService`) para atender múltiples clientes simultáneamente.
    * Cada nuevo cliente recibe un identificador único y su historial de mensajes.

2. **Conexión del Cliente:**

    * Cada cliente se ejecuta con `ChatApplication` que lanza la ventana de chat.
    * `ClienteSocket` se encarga de establecer la conexión TCP con el servidor.
    * Al conectarse, el cliente recibe un mensaje de bienvenida y todo el historial previo.

3. **Envío y Recepción de Mensajes:**

    * Los mensajes escritos en `TextField` se envían al servidor mediante `PrintWriter`.
    * El servidor recibe el mensaje, lo agrega al historial y lo reenvía a todos los clientes conectados.
    * Cada cliente muestra los mensajes en tiempo real en el `TextArea`.

4. **Gestión de Clientes:**

    * Cada cliente se maneja en un hilo separado mediante `ManejadorClienteMultihilo`.
    * Si un cliente envía el mensaje `"salir"`, se desconecta y se notifica a los demás usuarios.
    * El servidor mantiene la lista de clientes activos para poder enviar los mensajes grupales.

---

## Puntos Importantes

* **Servidor Multihilo:**

    * Permite hasta 10 clientes simultáneamente, se pueden modificar  (configurable en `MAX_CLIENTES`).
    * Uso de `CopyOnWriteArrayList` para mantener la lista de clientes y el historial de mensajes de manera segura en entornos concurrentes.
    * Uso de `AtomicInteger` para manejar el número de clientes de manera thread-safe.

* **Cliente Independiente:**

    * Cada cliente tiene su propia ventana de chat.
    * Puede abrir múltiples ventanas para simular varios usuarios en el mismo equipo.

* **Interfaz Gráfica (JavaFX):**

    * `TextArea` para mostrar mensajes.
    * `TextField` para escribir mensajes.
    * Botones para enviar mensajes y abrir nuevas ventanas de chat.

* **Historial Persistente en Sesión:**

    * Cada nuevo cliente recibe todos los mensajes enviados previamente durante la sesión actual.
---

## Estructura del Proyecto

```
com.example.chatsocket/
│
├── ChatApplication.java        # Clase principal que inicia la interfaz gráfica
├── ChatController.java         # Controlador de la interfaz (JavaFX)
├── ClienteSocket.java          # Cliente TCP para enviar y recibir mensajes
├── EchoServerMultihilo.java    # Servidor multihilo que gestiona los clientes
├── ManejadorClienteMultihilo.java # Hilo que maneja la comunicación con un cliente
├── hello-view.fxml             # Vista principal de chat
└── modalNombre.fxml            # (Opcional) Modal para ingresar nombre de usuario
```

