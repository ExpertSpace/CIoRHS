package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws InterruptedException {
        for (; ; ) {
            Thread.sleep(1000);
            try (ServerSocket serverSocket = new ServerSocket(8080)) {
                Socket socket = serverSocket.accept();

                BufferedReader bufferedReader =
                        new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));

                String request = bufferedReader.readLine();

                if (request != null) {
                    writeFile(request);

                    System.out.println("\u001B[32m" + request);
                }

                serverSocket.close();
                socket.close();
            } catch (Exception ignored) {

            }
        }
    }

    private static void writeFile(String data) {
        try {
            FileWriter fileWriter = new FileWriter("file.csv", true);
            fileWriter.write(data + "\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
