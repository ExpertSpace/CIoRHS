package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String[] request = bufferedReader.readLine().split(",");

            ComputerLocationRecorder recorder = new ComputerLocationRecorder();
            if (request != null) {
                System.out.println(Integer.parseInt(request[0]) + " " + request[1] + " " + request[2]);
                recorder.recordComputerLocation(Integer.parseInt(request[0]), Integer.parseInt(request[1]), request[2]);
            }
            recorder.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
