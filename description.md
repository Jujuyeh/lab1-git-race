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

## Setting up Redis

Redis provides persistent storage for the application. To use it, you first need to download and install Docker (https://www.docker.com/)

After the installation, you can use `docker-compose` to launch a Redis instance.

```bash
cd lab1-git-race/src/main/docker
docker-compose -f redis.yml up
```

This will start a Redis instance on port 6379.

## Which are the technologies used in the code

## How these technologies work

## What means each a specific piece or code

## Which is the purpose of a specific Java annotation


