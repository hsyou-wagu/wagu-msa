FROM java:openjdk-8-jdk
VOLUME /tmp
ADD ./build/libs/*.jar user.jar

ENTRYPOINT ["java","-jar","/user.jar"]
