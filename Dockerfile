FROM maven:3.6.3-jdk-14

ADD . /usr/src/webseek
WORKDIR /usr/src/webseek
EXPOSE 4567
ENTRYPOINT ["mvn", "clean", "verify", "exec:java"]