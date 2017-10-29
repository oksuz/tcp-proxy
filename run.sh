#!/bin/bash

/usr/bin/java -Xmx1g -Xms256m \
-DremoteHost=${REMOTE_HOST} \
-DremotePort=${REMOTE_PORT} \
-Dport=${PORT} \
-Dfile.encoding=UTF-8 \
-jar /opt/app/tcp-proxy.jar