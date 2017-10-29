# tcp-proxy

Simple tcp/ip proxy server written in java.

### Build

It uses [shadowJar](https://github.com/johnrengelman/shadow) jar plugin for making fat-jat. You can build fat-jar with following command:

```
gradle clean build
```

This command creates fat-jar under `build/libs/tcp-proxy.jar`.

### Run Application

Application takes three arguments for creating tcp server and connecting to remote host

- -DremoteHost=remoteServerOrIpAddress
- -DremotePort=remoteServerPort
- -Dport=tcpServerPort

```
java -DremoteHost=server.tld -DremotePort=10000 -Dport=5000 -jar tcp-proxy.jar
```

In the above example application accepts connection on `5000` port and forwards request to server.tld:10000 and also send server.tld:10000's responses to client

### Docker Build

This application also has docker image. You can pull image from [https://hub.docker.com/r/oksuz/tcp-proxy/](https://hub.docker.com/r/oksuz/tcp-proxy/)

You can run this image like this:

```
docker run --name my-tcp-proxy -p5000:5000 -eREMOTE_HOST=server.tld -eREMOTE_PORT=10000 -d oksuz/tcp-proxy
```

Default exposed port is `5000`. It means if you don't provide `PORT` env. variable default tcp server port is 5000. Also you can provide PORT env variable like this:

```
docker run --name my-tcp-proxy -p5001:5001 -ePORT=5001 -eREMOTE_HOST=server.tld -eREMOTE_PORT=10000 -d oksuz/tcp-proxy
```

### License

MIT