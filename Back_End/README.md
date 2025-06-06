# ChaTop

This project was generated with
  > Angular CLI version 14.1.0 | Java 17 | Spring Boot version 3.5.0 | Spring Data JPA | Spring Security | Spring Web | MySQL 8.0.33 | io.jsonwebtoken (JWT) version 0.11.5 | springdoc-openapi version 2.5.0 | JUnit | Maven

## Start the project

  ### MySql "chatop" BDD Setting

  . Create a MySQL database named "chatop"

  . Create a user with full privileges on this database

  . Replace credentials of this chatop BDD user in "spring.datasource.username" and "spring.datasource.password" of Back_End/src/Ressources/application.properties file and save.

  . Create the database schema by executing the SQL script available in `Front_End/ressources/sql/script.sql`

  ### Git clone

  > git clone https://github.com/mag-alo/ChaTop.git

  ### Install and Launch Front_end

  In a terminal, under Front_End repository root, 
  
  Install dependencies:

  > npm install

  And Launch Front_End:

  > npm run start;

  ### Launch Back_end 
  
  In Spring Boot Dashboard, click

  > Run

## Back_End Documentation then available on 

http://localhost:9000/swagger-ui/index.html#/    
