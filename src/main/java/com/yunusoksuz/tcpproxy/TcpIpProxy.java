package com.yunusoksuz.tcpproxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by oksuz on 29/10/2017.
 */
public class TcpIpProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(TcpIpProxy.class);

    private final String remoteIp;
    private final int remotePort;
    private final int port;

    public TcpIpProxy(String remoteIp, int remotePort, int port) {
        this.remoteIp = remoteIp;
        this.remotePort = remotePort;
        this.port = port;
    }

    public void listen() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            LOGGER.info("listening...");
            while (true) {
                Socket socket = serverSocket.accept();
                startThread(new Connection(socket, remoteIp, remotePort));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void startThread(Connection connection) {
        Thread t = new Thread(connection);
        t.start();
    }
}
