package com.yunusoksuz.tcpproxy;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by oksuz on 29/10/2017.
 */
public class TcpIpProxy {

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
