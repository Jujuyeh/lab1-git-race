# Table of Contents

1. [Automatic setup process](#automatic-setup-process)
1. [Manual setup process](#manual-setup-process)
    1. [How to build the code](#how-to-build-the-code)
    1. [How to test the code](#how-to-test-the-code)
    1. [How to deploy the code in a server](#how-to-deploy-the-code-in-a-server)
    1. [Setting up Redis](#setting-up-redis)
1. [Using Redis in your application](#using-redis-in-your-application)
1. [Which are the technologies used in the code](#which-are-the-technologies-used-in-the-code)
1. [How these technologies work](#how-these-technologies-work)
1. [What means each a specific piece or code](#what-means-each-a-specific-piece-or-code)
1. [Which is the purpose of a specific Java annotation](#which-is-the-purpose-of-a-specific-java-annotation)
1. [How to implement code following TDD best practices](#how-to-implement-code-following-tdd-best-practices)

## Automatic setup process

By using [Docker Compose](https://docs.docker.com/compose/) the whole project can be built and deployed locally, so that
the webapp is accessible at http://localhost:8081.

Some of the command you may use (check `man docker-compose` for more):

```bash
docker-compose build  # Build the images
docker-compose up     # Build the images and start the services
docker-compose up -d  # Same as previous but in detached mode (no visible logs of Tomcat)
docker-compose stop   # Stop all the services but keep containers in storage for future
docker-compose down   # Stop all the services and destroy the allocated containers
```

Note that if you are modifying the source code, you don't need to keep bringing down the services, just call `up`
again. this will check for the need to recreate any image and hot-reload it, leaving everything else the same.

## Manual setup process

### How to build the code

This application uses [Gradle](http://gradle.org) to build the code. Please, refer to the [Gradle user guide](https://docs.gradle.org/current/userguide/installation.html) for its installation. Once it is installed just do:

```bash
cd lab1-git-race
gradle build
```

After a few seconds, "BUILD SUCCESSFUL" indicates that the build has completed. There you’ll find generated WAR file inside *libs* folder under *build* directory.

### How to test the code

Testing the code can be done automatically by using the JUint library for unit tests. Integration tests may also use specific components of the Spring framework to help with this task.

Project is tested upon each build. However, running all the tests can be requested manually, just do:

```bash
cd lab1-git-race
gradle check
```

In order to run unit test only, use instead:

```bash
cd lab1-git-race
gradle test
```

### How to deploy the code in a server

For developing stages of the project, it is possible to run a *ad-hoc* Tomcat server, just do:

```bash
cd lab1-git-race
gradle bootRun
```

Refer to [Apache Tomcat documentation](https://tomcat.apache.org/tomcat-8.0-doc/deployer-howto.html) about how to deploy a WAR file, once [deliverables have been built](#how-to-build-the-code).

### Setting up Redis

Redis provides persistent storage for the application. To use it, you first need to download and install [Docker](https://www.docker.com/)

To start a Redis instance run:

```bash
docker run -p 6379:6379 --name my_redis -d redis:alpine
```

To remove the Redis instance run:

```bash
docker stop my_redis && docker rm my_redis
```

Note that by default, Spring looks for Redis at localhost at the port 6379. If you want to connect to a Redis instance in
a different IP or port, you need to either modify the variables `spring.redis.host` and `spring.redis.port` in the file
application.properties, or pass the environmental variables `SPRING_REDIS_HOST` and `SPRING_REDIS_PORT` when starting
Spring.


## Using Redis in your application

Here is an example usage of Redis:

```Java
public class Example {
    /** your storage */
    @Autowired
    private StringRedisTemplate sharedData;

    public String example(...) {
        /** write a key/value pair */
        String value = "hello";
        sharedData.opsForValue().set("key", value);
        /** read a key */
        String readValue = sharedData.opsForValue().get("key");
    }
}
```

## Which are the technologies used in the code

## How these technologies work

### Junit4

JUnit is one of the most popular testing frameworks for Java. Although JUnit5 is the newest version of the framework, JUnit4 is still used in many projects (e.g for [TDD](#how-to-implement-code-following-tdd-best-practices)). Let's see [how it works](#how-junit4-works).

### Spring

Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run".

We take an opinionated view of the Spring platform and third-party libraries so you can get started with minimum fuss. Most Spring Boot applications need very little Spring configuration.

In fact, a professor of Web Engineering demonstrated how to mount URL Shortener in less than 5 minutes with Spring Boot.

#### Some advantages over using spring

* It is very easy to develop Spring Based applications with Java or Groovy.
* It reduces lots of development time and increases productivity.
* It provides Embedded HTTP servers like Tomcat, Jetty etc. to develop and test our web applications very easily.
* It provides CLI (Command Line Interface) tool to develop and test Spring Boot(Java or Groovy) Applications from command prompt very easily and quickly.
* And much more.

The sources used and where much more information can be found:

* [spring.io](https://spring.io/projects/spring-boot)
* [hwww.journaldev.com/7969/spring-boot-tutorial](https://www.journaldev.com/7969/spring-boot-tutorial)

### Redis

Redis is an open source (BSD licensed), in-memory data structure store, used as a database, cache and message broker.

It supports data structures such as strings, hashes, lists, sets, sorted sets with range queries, bitmaps, hyperloglogs, geospatial indexes with radius queries and streams.


#### Some advantages over using Redis

* It allows storing key and value pairs as large as 512 MB.
* It uses its own hashing mechanism called Redis Hashing.
* It offers data replication.
* Its cache can withstand failures and provide uninterrupted service
* It has clients in all the popular programming languages.

Sources and more information:

* [redis.io](https://redis.io)
* [https://dzone.com/articles/10-traits-of-redis](https://dzone.com/articles/10-traits-of-redis)


## How these technologies work

### How Junit4 works

Creating a test with JUnit4 is really easy. In order to do it follow these steps:

1. Create the class you want to test (e.g. JavaClass.java).
2. Create a class for testing (e.g. JavaClassTest.java).
3. For each test you want to create, write a method with @Test tag above.
4. Run the test.

> **Note:** You may to import some JUnit4 libraries.

For further information, you can visit <https://github.com/junit-team/junit4/wiki/Getting-started>.

### To start a project with Spring boot

#### Prerequisites

1. We are going to use the gradle tool, so it must be installed.

#### Create Spring Boot proyect

1. Create gradle proyect

    ```bash
    gradle init
    ```

    Let's select the desired options, using java as language.

1. Transform into a Spring Boot Web application
    Edit the class App at src/... and adds the following imports:

    ```java
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    ```

    Above the definition of the App function adds : @SpringBootApplication
    And finally, within the main function write `SpringApplication.run(App.class, args);`

1. Editing the gradle builder
    Add the following plugins and dependencies:

    ```java
    id 'org.springframework.boot' version '2.1.8.RELEASE'
    id 'io.spring.dependency-management' version '1.0.8.RELEASE'

    implementation 'org.springframework.boot:spring-boot-starter-web'
    ```

1. This is the initial application of Spring Boot.

1. Now you can add the actions that are triggered when making requests to endpoints.

## What means each a specific piece or code

## Which is the purpose of a specific Java annotation

### @Controller

As spring boot it follows the architecture Model-View-Controller, @Controller annotation indicates that the annotated class is a controller. It is a specialization of @Component and is autodetected through classpath scanning. It is typically used in combination with annotated handler methods based on the @RequestMapping annotation.
In short, it serves to indicate that it is the controller class of the architecture.

### @Value

To talk about the @Value annotation, first we must talk about the spring boot properties file.
Property files are used to keep a number of properties in a single file to run the application in any environment. In Spring Boot, the properties file is stored in the application.properties file.
The syntax of the properties file is

```bash
app.{"key"}="value"
```

The @Value annotation is used to read the value of some of the properties stored in the properties file.
In this file, you can see data about the environment, about values or variables or even properties of the application as the listening port.
The syntax of the @Value annotation is

```java
@Value("${property_key_name}")
```

### @GetMapping("/")

@GetMapping annotation maps HTTP GET requests onto specific handler methods. It is a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.GET).
If the argument is "/", it means that the method whose annotation is @GetMapping("/") will be triggered when a request is made to the "root" of the web.
If the argument is of the type "string/string...etc", it means that the method whose annotation is @GetMapping("string/string...") will be triggered when a request is made to this endpoint of the web.

### @PostMapping("/")

@PostMapping is specialized version of @RequestMapping annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.POST).
@PostMapping  annotated methods handle the HTTP POST requests matched with given URI expression.

Sources:

* [https://howtodoinjava.com/spring5/webmvc/controller-getmapping-postmapping/](https://howtodoinjava.com/spring5/webmvc/controller-getmapping-postmapping/)


### @Configuration

The @ Configuration annotation is used to indicate that the class that follows it, has one or more @Bean methods. The @Bean annotation defines objects that are
managed by the Spring Container.

## How to implement code following TDD best practices

Coding following TDD practices involves creating code using a very short development cycle. When you use TDD, you must write a unit test (e.g. using JUnit libraries) before you implement the actual funcionality. Therefore, your goal is passing all the tests. When you have written tests that verify code works properly, and all these tests pass, you know your code works as you intented to.

These are the steps of TDD development cycle:

1. Add a test: create a test for every new feature you want to add to the code. Each tests may match a test case (seen in Software Engineering and other related subjects).
2. Run all the tests, the last test added should fail: All the test previously added should pass, but the one added in step 1 should fail. Otherwise, something is wrong. If you are following well these steps, this is not something likely to happen.
3. Write the code so the test passes: Following the theory, you must write the smallest piece of code so the test passes. This might sound useless, but, for example, if in the first test you are testing a test case that returns 1, you can simply write `return 1;` in the code, although you know in the future you may change the code. Keep in mind that when you already have several tests, the new test has to pass, but the older tests too.
4. Run all the tests: Now is the moment when you verify that the piece of code you have written is correct. Every test (the new one and the older ones) has to pass.
5. Refactor your code: now that you know that your code works, you may want to rewrite some parts to make it more legible or better structured. After your changes, run all your tests so you know that you have not broken the code. Now it is time to go back to step 1 and add a new test for a new test case.

You should avoid some practices, for example, dependency between tests. More documentation about TDD can be found [here](https://en.wikipedia.org/wiki/Test-driven_development).

Note that following TDD practices might not sound very useful when adding a small and simple feature, but adopting this practice is very helpful in a complex project, in which each time you add a piece of code it may break the rest of it and make it difficult to detect where the error is.

## Social-Bar

A sidebar has been added to the main page of the website in order to share the website with friends and family through various social networks.

In order to be able to share it is enough to select the icon of the network through which you want to send the link of the page. As a result, the web page will be opened in a pop-up browser page to share.

## Record of visits by ip address

It has developed a simple record of visits with the aim that the user can know how many times you have visited the website based on your IP address.

## Tramway arrivals at EINA's tramway stop

Show information about tramway arrivals at closest EINA tramway stop: Campus Río Ebro. That way students and professors can know when is going to arrive next tramway so they can get on the tramway in time.

Implementation has been possible thanks to Zaragoza's town hall API.
