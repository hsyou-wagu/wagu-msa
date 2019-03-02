FROM java:openjdk-8-jdk
VOLUME /tmp
ADD ./build/libs/*.jar search.jar

ENTRYPOINT ["java","-jar","/search.jar"]
