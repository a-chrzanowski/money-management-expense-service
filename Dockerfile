FROM maven:3.8.6-eclipse-temurin-17
RUN useradd --create-home --shell /bin/bash esuser
USER esuser
WORKDIR /home/es
COPY */target/mm-expense-service-*.jar /home/es/mm-expense-service.jar
CMD ["java", "-jar", "/home/es/mm-expense-service.jar"]