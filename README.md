# spring-consul-showcase

## Run Consul Agent
```
consul agent -dev
```

```
./gradlew build
```

```
java -jar hello-service/build/libs/hello-service.jar
java -jar goodby-service/build/libs/goodby-service.jar
java -jar goodby-service/build/libs/goodby-service.jar --server.port=8082
java -jar goodby-service/build/libs/goodby-service.jar --server.port=8083
java -jar cli/build/libs/cli.jar
```

Got to `http://localhost:8500` to check Consul dashboard
