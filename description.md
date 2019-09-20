# Table of Contents

1. [How to build the code](#how-to-build-the-code)
2. [How to test the code](#how-to-test-the-code)
3. [How to deploy the code in a server](#how-to-deploy-the-code-in-a-server)
4. [Which are the technologies used in the code](#which-are-the-technologies-used-in-the-code)
5. [How these technologies work](#how-these-technologies-work)
6. [What means each a specific piece or code](#what-means-each-a-specific-piece-or-code)
7. [Which is the purpose of a specific Java annotation](#which-is-the-purpose-of-a-specific-java-annotation)

## How to build the code

This application uses [Gradle](http://gradle.org) to build the code. Please, refer to the [Gradle user guide](https://docs.gradle.org/current/userguide/installation.html) for its installation. Once it is installed just do:

```bash
cd lab1-git-race
gradle build
```

After a few seconds, "BUILD SUCCESSFUL" indicates that the build has completed. There you’ll find generated WAR file inside *libs* folder under *build* directory.

## How to test the code

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

## How to deploy the code in a server

For developing stages of the project, it is possible to run a *ad-hoc* Tomcat server, just do:

```bash
cd lab1-git-race
gradle bootRun
```
Refer to [Apache Tomcat documentation](https://tomcat.apache.org/tomcat-8.0-doc/deployer-howto.html) about how to deploy a WAR file, once [deliverables have been built](#how-to-build-the-code).

## Which are the technologies used in the code
### Junit4
JUnit is one of the most popular testing frameworks for Java. Although JUnit5 is the newest version of the framework, JUnit4 is still used in many projects. Let's see [how it works](#how-junit4-works).
  

## How these technologies work
### How Junit4 works
Creating a test with JUnit4 is really easy. In order to do it follow these steps:
1. Create the class you want to test (e.g. JavaClass.java).
2. Create a class for testing (e.g. JavaClassTest.java).
3. For each test you want to create, write a method with @Test tag above.
4. Run the test.
> **Note:** You may to import some JUnit4 libraries.

For further information, you can visit https://github.com/junit-team/junit4/wiki/Getting-started.

## What means each a specific piece or code

## Which is the purpose of a specific Java annotation


