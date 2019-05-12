# chronogg4j
Java application that will press the daily ChronoGG coin button.

## Usage
Run the ```jar``` file and pass the chronogg authorization JWT token. This only needs to be passed in once.
After the first run, you can change the token in ```application.properties``` or by passing the token in again.

It is ideally used with a cron-task on a RaspberryPi.

## Building project from source
```mvn clean compile package``` will compile and package to your ```/target/``` folder. The file will be named ```chronogg4j.jar```
