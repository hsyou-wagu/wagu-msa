FROM java:openjdk-8-jdk
VOLUME /tmp
ADD ./build/libs/*.jar post.jar

ENTRYPOINT ["java","-jar","/post.jar"]
