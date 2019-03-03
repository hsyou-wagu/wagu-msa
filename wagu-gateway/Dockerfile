
FROM java:openjdk-8-jdk

WORKDIR /server/program/bootapp
ADD ./build/libs/*.jar gateway.jar
EXPOSE 8761

CMD ["/bin/bash", "-c", "exec java $JAVA_OPTS -jar gateway.jar"]
