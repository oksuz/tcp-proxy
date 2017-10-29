package com.yunusoksuz.tcpproxy;

/**
 * Created by oksuz on 29/10/2017.
 */
public class AppMain {

    public static void main(String args[]) {

        String remote = "test.com"; // host
        int remotePort = 5001; // remote tcp port
        int serverPort = 5000; // proxy server port

        TcpIpProxy tcpIpProxy = new TcpIpProxy(remote, remotePort, serverPort);
        tcpIpProxy.listen(); // server accepts connection on serverPort
    }


}
