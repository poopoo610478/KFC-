# 1. 建立後端 (Spring Boot) 的映像檔
FROM maven:3.9.4-eclipse-temurin-21 AS backend-build
WORKDIR /app
COPY ./proj-chufa /app
RUN mvn clean package -DskipTests

# 2. 建立前端 (Vue) 的映像檔
FROM node:18 AS frontend-build
WORKDIR /app
COPY ./vue-chufa /app
RUN npm install && npm run build

# 3. 整合前後端到最終映像檔
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=backend-build /app/target/*.war app.war
COPY --from=frontend-build /app/dist /app/public   

# 4. 啟動 Spring Boot 應用程式
EXPOSE 8080
CMD ["java", "-jar", "app.war"]
