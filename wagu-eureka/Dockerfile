FROM java:openjdk-8-jdk
VOLUME /tmp
ADD ./build/libs/*.jar eureka.jar

ENTRYPOINT ["java","-jar","/eureka.jar"]
