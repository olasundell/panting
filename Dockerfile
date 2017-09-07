FROM openjdk:8u141

ARG JAR_NAME
ADD ${JAR_NAME} app.jar
RUN sh -c 'touch app.jar'
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8123:8123
