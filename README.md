# prototraffic-java
This repository contains with the following:
- A Spring Boot Application with instrumentation setup for generating events using drdroidapi-java
- A traffic simulator for the above Spring Boot Application


## Import DrDroid Java SDK
Add the following dependency in your gradle folder to import drdroid java sdk into your project
```
implementation group: 'io.drdroid', name: 'api-java', version: '1.0.0'
```

## Build Project
Run the following command to install java dependencies and traffic simulator
```
./gradlew clean build
```

## Generating custom events for Doctor Droid
```
// Put the following as the class imports
import io.drdroid.api.DrDroidClient;
import io.drdroid.api.models.ClientConfig;


// Add events like this
drDroidClient.send("sample-workflow-name", "sample-state", kvPairs);
```

You'll need to setup a few environment variables for the SDK configuration. Here is complete documentation about the SDK [drdroidapi-java](https://github.com/DrDroidLab/drdroidapi-java) and how to configure it.

## Run the SpringBoot application
Running the following command to start the java application on port 8082. It is not dockerized.
```
java -jar build/libs/sandbox-0.0.1-SNAPSHOT.jar
```


## Run the traffic simulation
Run the following command to start the simulated traffic.
```
curl -X GET http://localhost:8082/start
```

Run the following command to start the simulated traffic.
```
curl -X GET http://localhost:8082/stop
```

## Shutting down the application
Run the following command to stop the application
```
curl -X POST http://localhost:8082/actuator/shutdown
```