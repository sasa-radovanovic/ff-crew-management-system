# ff-crew-management-system (Frequent Flyers Crew Management System)
Crew management system by Frequent Flyer

First of all - **this is not commercial application and is still work in progress**. This web service emulates crew management process at a commercial airline. It consists of a backoffice (access limited only to administration personel) and crew portal (access for every crew).

### Motivation

This application connects two of my big passions - aviation and coding. I am aware that airlines have multi-billion software for this purpose, with a lot more functionalities and features. This project does not aim to be a commercial one, it is just my personal homage to airlines, their IT sectors and my view on this mega-complex problem. 

From the technical point of view - I wanted to try MongoDB in Java-based environment (I do have two years MongoDB experience, but solely in NodeJS environment). Being more core JavaEE than Spring developer, this also the perfect chance to go a bit deeper with Spring.
Beside this, I wanted to try AngularJS 1 with templating engine other than Bootstrap. 

### Software components

This small project is only around 50% complete. There are some stuff that i would definitely rewrite, but since this was written in just 3 weeks using 2-3 hours/day, i am pretty pleased with the way it turned out. You may notice that it's missing unit tests.

* Programming language - Java 8
* Middleware framework [Spring](https://spring.io/) (Spring Boot, Spring MVC, Spring Security)  
  * Spring Boot 
  * Spring Security
  * Spring MVC
  * Spring JPA
* Database - MongoDB 3.2
* Frontend
  * AngularJS (Note that there are two separate Angular applications, more on use case link)
  * [Foundation](http://foundation.zurb.com/)

### Installation

In order to run instance, download code. Download Java 8 (JDK or JRE, depending what do you plan to do with the code) if you do not have one on your machine. Download and set MongoDB, and create database for the application (called "cms_database"). Download and setup maven. Once you downloaded the project, run mvn package and create jar-with-dependencies. As far as system specific things, you can change database by editing SpringMongoConfiguratio.java (I am aware that hard coding database name / configuration into code is not a good practice - this should be placed in .application file):

```
	@Override
	protected String getDatabaseName() 
	{
		return "cms_database";
	}
```

There are still a ton of test / debug calls in CrewManagementSystemApplication.java (run method), so do not be scared when you start the application and get console logs.

Deployment:

- Deploying from IDE

Import project into STS / Eclipse IDE. I encourage you to use STS. Although, it is basically Eclipse adapted by guys behind Spring, it does make coding and development much easier. Run maven update. Simply click on "Run as > Java Application/Spring Boot application (depending on your IDE)" on that exact file. In console there should stack about application start-up procedure. After the following line:
```
Started CrewManagementSystemApplication in 25.689 seconds
```
Your own application has been started. Navigate your browser to http://localhost:8080/  and follow login screen. If you login with admin/admin credentials you will be redirected to backoffice portal. 

- Deploying from file system

Make sure you have set JAVA_HOME environment variable. Build application using:

```
mvn clean compile -e
```
And package it with:
```
mvn clean package -e
```

Navigate your terminal (or command prompt) to PROJECT_PATH\target and run crew-management-system fat jar with:
```
java -jar crew-management-system.jar
```
Of course - the name of the application is different, but you get the idea.

And... That's it. If everything went as planned - you should navigate your browser to http://localhost:8080 and the app is there.
Note that during boot there will be heavy logs in console/terminal since application initializes all system components and prints some database manipulation.

### Design patterns

I did try to comply to Clean Code principles as much as i did, although i am aware that code deviates in some places from it. There should be one serious code revision after which i could say that it's been done properly. Nonetheless, i tried using as much as GoF Design patterns as i could.

Some of them are:

Creational patterns | Structural patterns | Behavior patterns
------------ | ----------------- | -----------------
Factory Pattern | Composite Pattern  | Front Controller Pattern
Abstract Factory Pattern | Facade Pattern | Data Access Object Pattern
Singleton Pattern | MVC Pattern | Template Pattern
Builder Pattern |  | Dependency Injection
Prototype Pattern |  | 

### Use cases and printscreens

Application-go-through will be available soon.

### Tests

Tests are not written yet unfortunately. Taking into account the time i had, i did not force TDD methodology here. 

### Contributors

I am the sole contributor to this small project and for all other information you can contact me by mail:
- sasa1kg@yahoo.com
- sasa.radovanovic@live.com

Don't forget - Eat, sleep, fly, repeat! :)

### License

This code is available through MIT licence. 

