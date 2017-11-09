package com.yunusoksuz.tcpproxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(Connection.class);

    public Connection(Socket clientsocket, String remoteIp, int remotePort) {
        this.clientsocket = clientsocket;
        this.remoteIp = remoteIp;
        this.remotePort = remotePort;
    }

    @Override
    public void run() {
        LOGGER.info("new connection {}:{}", clientsocket.getInetAddress().getHostName(), clientsocket.getPort());
        try {
            serverConnection = new Socket(remoteIp, remotePort);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        LOGGER.info("Proxy {}:{} <-> {}:{}", clientsocket.getInetAddress().getHostName(), clientsocket.getPort(), serverConnection.getInetAddress().getHostName(), serverConnection.getPort());

        new Thread(new Proxy(clientsocket, serverConnection)).start();
        new Thread(new Proxy(serverConnection, clientsocket)).start();
        new Thread(() -> {
            while (true) {
                if (clientsocket.isClosed()) {
                    LOGGER.info("client socket ({}:{}) closed", clientsocket.getInetAddress().getHostName(), clientsocket.getPort());
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
                LOGGER.info("closing remote host connection {}:{}", serverConnection.getInetAddress().getHostName(), serverConnection.getPort());
                serverConnection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
