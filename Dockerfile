FROM openjdk:17

WORKDIR /case

COPY build/libs/case-0.5.jar .

EXPOSE 8080

ENTRYPOINT ["java","-jar","case-0.5.jar"]

CMD["jar", "-bootJar", "/build/libs/case-0.5.jar"]