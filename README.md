# ChaTop

This project was generated with
  > Angular CLI version 14.1.0 | Java 17 | Spring Boot version 3.5.0 | Spring Data JPA | Spring Security | Spring Web | MySQL 8.0.33 | io.jsonwebtoken (JWT) version 0.11.5 | springdoc-openapi version 2.5.0 | JUnit | Maven

## Start the project

  ### MySql "chatop" BDD Setting

  . Create a MySQL database

  . Create a user with full privileges on this database

  . Create three new Environnement Variables named as following replacing values "YOUR_XXX" with yours : 

    DB_chatop_URL = "YOUR_DATABASE_NAME"
    DB_chatop_Username = "YOUR_DATABASE_USER_NAME"
    DB_chatop_Password = "YOUR_DATABASE_USER_PASSWORD"

  . Create the database schema by executing the SQL script available in `Front_End/ressources/sql/script.sql`

  ### Token Setting 

  In a terminal, generate a secrete value which will be used to generate token.
  > echo -n "my-secret-value" | sha256sum

  . Create a new Environnement Variable named as following replacing value "YOUR_XXX" with yours : 

    API_CHATOP_TOKEN = "YOUR_JUST_GENERATED_SECRET_VALUE"

  ### Git clone

  > git clone https://github.com/mag-alo/ChaTop.git

  ### Launch Back_end 
  
  In a terminal, under Back_End repository root (cd Back_End), 

  > ./mvnw spring-boot:run;

  ### Install and Launch Front_end

  In a terminal, under Front_End repository root (cd Front_End), 
  
  Install dependencies:

  > npm install

  And Launch Front_End:

  > npm run start;

## Back_End Documentation will be so available on 

http://localhost:9000/swagger-ui/index.html#/    
