# Shakesperean Pokedex

A [Quarkus](https://quarkus.io/) based REST API that, given a Pokemon name, returns its description translated in
 Shakesperean style.

## Technology stack
The project is built on top of Quarkus framework. The reason I did so is that it comes with a wide range of [open source
and opinionated libraries]() and [extensions]().
Moreover it provides built-in support for containerized application creation, kubernetes deployment and native images 
leveraging [GraalVM]() features.
Finally I also wanted to explore Quarkus capabilities with respect to other well known Java frameworks like Springboot.

## Project structure
//todo

## Running the application
//todo

## Testing the application
//todo

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
