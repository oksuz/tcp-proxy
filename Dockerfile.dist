FROM ubuntu:16.04

MAINTAINER Yunus Oksuz <yunusoksuz@gmail.com>

# install required packages
RUN apt-get update -y
RUN apt-get install -y default-jre nginx tzdata locales

# configure timezone
RUN echo "Europe/Istanbul" > /etc/timezone
RUN rm -f /etc/localtime
RUN dpkg-reconfigure -f noninteractive tzdata

RUN mkdir -p /opt/app

# copy app
COPY build/libs/tcp-proxy.jar /opt/app/tcp-proxy.jar

# init script
COPY run.sh /opt/app/run.sh
RUN chmod +x /opt/app/run.sh

ENV PORT @PORT@
EXPOSE @PORT@

WORKDIR /opt/app

CMD ["/bin/sh", "run.sh"]