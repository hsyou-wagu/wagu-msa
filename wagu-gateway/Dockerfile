FROM java:openjdk-8-jdk
VOLUME /tmp
ADD ./build/libs/*.jar gateway.jar

ENTRYPOINT ["java","-jar","/gateway.jar"]
