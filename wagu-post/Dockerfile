FROM java:openjdk-8-jdk

WORKDIR /server/program/bootapp
ADD ./build/libs/post.jar post.jar

CMD ["/bin/bash", "-c", "exec java $JAVA_OPTS -jar post.jar"]
