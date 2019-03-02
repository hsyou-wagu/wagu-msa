FROM java:openjdk-8-jdk
VOLUME /tmp
ADD ./build/libs/*.jar config.jar

ENTRYPOINT ["java","-jar","/config.jar"]
