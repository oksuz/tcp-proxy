package com.yunusoksuz.tcpproxy;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by oksuz on 29/10/2017.
 */
public class Connection implements Runnable {

    private final Socket clientsocket;
    private final String remoteIp;
    private final int remotePort;
    private Socket serverConnection = null;

    public Connection(Socket clientsocket, String remoteIp, int remotePort) {
        this.clientsocket = clientsocket;
        this.remoteIp = remoteIp;
        this.remotePort = remotePort;
    }

    @Override
    public void run() {
        try {
            serverConnection = new Socket(remoteIp, remotePort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (serverConnection == null) {
            return;
        }

        new Thread(new Proxy(clientsocket, serverConnection)).start();
        new Thread(new Proxy(serverConnection, clientsocket)).start();
        new Thread(() -> {
            while (true) {
                if (clientsocket.isClosed()) {
                    closeServerConnection();
                    break;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
            }
        }).start();
    }

    private void closeServerConnection() {
        if (serverConnection != null && !serverConnection.isClosed()) {
            try {
                serverConnection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
