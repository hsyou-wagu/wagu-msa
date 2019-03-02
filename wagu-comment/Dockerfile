FROM java:openjdk-8-jdk
VOLUME /tmp
ADD ./build/libs/*.jar comment.jar

ENTRYPOINT ["java","-jar","/comment.jar"]
