# Shakespearean Pokedex

A [Quarkus](https://quarkus.io/) based REST API that, given a Pokemon name, returns its description translated in
 Shakespearean style.

## Technology stack
The project is built on top of Quarkus framework. The reason I did so is that it comes with a wide range of open source
and opinionated libraries and useful extensions. MicroProfile, SmallRye fault tolerance, Open tracing, OpenAPI libraries
are just few of them I used in this project.
Moreover it provides built-in support for testing, containerized applications creation, Kubernetes deployment and native 
images leveraging [GraalVM](https://www.graalvm.org/) features.
Finally I also wanted to explore Quarkus capabilities with respect to other well known Java frameworks like Springboot.
I also enjoyed Quarkus live-reloading feature which is not so common while developing in Java.

## Running the application
In order to run the project the following dependencies are required: 
- any distribution of JDK8 or 11+
- optionally [GraalVM](https://www.graalvm.org/) for native image build and execution
- optionally [Docker](https://docs.docker.com/) to build project images and run the containerized version of this project

The simplest way to run the project is to use the [Maven wrapper](https://maven.apache.org/) bundled in this project. 
Run the following from the root directory:

`./mvnw compile quarkus:dev`

Another possible approach is to build the application jar first and then run it using java lib.

```
./mvnw compile 
java -jar target/shakespearean-pokedex-1.0.0-SNAPSHOT-runner.jar
```

## Tests
Unit and integration tests are defined into src/test/java directory. Whenever possible I did not rely on Quarkus application
context as I wanted to keep the test as pure and independent as possible. I structured tests in *unit*, *integration* and 
*integration_native* packages to keep them more readable and in order to split their execution in isolated phases.

*unit* package contains pure unit tests. Amongst those builders, mappers and main service tests are defined.
*integration* package contains integration tests, that is either tests which involved external services (stubbed with [Wiremock](http://wiremock.org/))
or tests relying Quarkus application context (see tests marked with _@QuarkusTest_ annotation). REST endpoint and fault tolerance
tests are included here as well.  
finally *integration_native* package include a test to be executed against the native image of this project.

Execution phases are defined with the help of (surefire)[https://maven.apache.org/surefire/maven-surefire-plugin/] and 
(failsafe)[https://maven.apache.org/surefire/maven-failsafe-plugin/] maven plugins. 
If interested, see pom.xml configuration for further details.

To run unit tests: 

`./mvnw test`

To run both unit and integration tests: 

`./mvnw integration-test` or  `./mvnw verify`

To run integration tests only: 

`./mvnw integration-test -Dskip.unit.test=true`

To run all tests including native image ones:
`./mvnw verify -Pnative`

To run native tests only:

`./mvnw verify -Pnative -Dskip.unit.test=true -Dskip.integration.test=true`

## Extras
### Opentracing
//todo: improve

To start the tracing system to collect and display the captured traces:

`docker run -e COLLECTOR_ZIPKIN_HTTP_PORT=9411 -p 5775:5775/udp -p 6831:6831/udp -p 6832:6832/udp -p 5778:5778 -p 16686:16686 -p 14268:14268 -p 9411:9411 jaegertracing/all-in-one:latest`

UI is available at http://localhost:16686/search

### Docker
//todo

### Native image
//todo
