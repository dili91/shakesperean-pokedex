# shakesperean-pokedex

A REST API that, given a Pokemon name, returns its Shakesperean description.
Documentation available at http://localhost:8080/swagger-ui/

## Technology stack
//todo

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
