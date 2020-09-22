# EmployeeRecords

Employee Records API is developed to maintain the Information related to several company Employees.

Build and Run

It is a maven Spring-Boot project with Java 8 version so we need to just run the following command to build the application, first need to go to the path where is pom.xml file located:
mvn clean install -U

-U required if any dependency not exist locally, it will download it.

In the same location, need run the following command to run the application:
mvn spring-boot:run

Above command will start the Tomcat container on default port 8080. For terminating the application, need to use:
ctrl+c

But this command will not kill kill the port usage, so need manually kill it. In Windows, below commands can be used:
netstat -ano | findstr :<PORT>   ---   will give PID information
taskkill /PID <PID> /F   ---   With using PID task can be killed

Swagger UI is implemented, so the API Documentation can accessed using the following URL in the browser and we can test the API:
http://localhost:8080/swagger-ui.html#

There are 2 sql files for initializing DB, one for mysql (which I used while implementation API), another one for h2 (which can be used for testing) respectively:
data-mysql.sql
data-h2.sql

DB properties and DB platform configuration can be done through "application.properties" in "resources" folder:
spring.datasource.platform=h2

I make it h2 DB default and below will be the DB access URL for H2 DB:
http://localhost:8080/h2-console

In MySql DB I used stored procedure (GetAverageSalary) for calculating average salary for the company, then called this procedure in JPA.
In H2 DB I used another approach, first fetch all employees for given company and then calculated average salary.

I added JWT for securing save, update and delete requests, but other requests is accessible without authentication.

For authentication, below POST request can be used, with provided body and credentials:
http://localhost:8080/authenticate
{
	"username" : "admin",
	"password" : "admin"
}

For testing purpose, Mockito framework is used.
For logging purpose, slf4j Logging Facade is used.
For handling exceptions, ControllerAdvice is used.