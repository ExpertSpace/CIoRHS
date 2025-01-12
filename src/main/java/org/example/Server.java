package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Сервер запущен на порту 8080");

            while (true) {
                // Ожидаем подключения клиента
                Socket socket = serverSocket.accept();
                System.out.println("Клиент подключен: " + socket.getRemoteSocketAddress());

                // Создаем новый поток для обработки клиента
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}