FROM maven:3.8.6-eclipse-temurin-17
RUN useradd --create-home --shell /bin/bash esuser
USER esuser
WORKDIR /home/es
COPY */target/money-management-expense-service-*.jar /home/es/money-management-expense-service.jar
CMD ["java", "-jar", "/home/es/money-management-expense-service.jar"]