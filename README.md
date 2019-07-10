##### Microservices Challenge

# What is this application? 

### song-service
- Gives you music title by ID
- Usage: host:port/song/{id}

### playlist-service
- Gives you a song array, containing only IDs
- Usage: host:port/playlist/{id}

### app-service
- Recieves the playlist ID and returns the title of each song
- Usage: host:port/app/{id}

----

# Running the application

## Run the SQL scripts to create the song and playlist databases
1. First you need to open your favourite DBMS and start your favourite web server
2. Run the 'dump.sql' script that are in the '~/final-2/playlist-service/src/main/resources'.
3. Run the 'dump.sql' script that are in the '~/final-2/song-service/src/main/resources'.
4. This will let the databases ready for the services to use.

## Building the services .jar files
1. Go to each service (song-service/playlist-service/app-service) project folder.
2. On song-service and playlist-service, add your database credentials to the src/main/resources/application.properties file.
3. Run the command ```gradle wrapper```.
4. Run the command ```./gradlew clean build```.
5. This will let all the services ready to start.

## Starting Eureka
1. Copy your Eureka Server .war file to the ~/apache-tomcat-{version}/webapps folder.
2. Run the command 'bin/catalina.sh run into the ~/apache-tomcat-{version} folder to run the Apache Tomcat server.
3. This will start Eureka Server.

## Running the services
1. Run the command ```java -jar build/libs/<service>.jar``` inside each service (song-service/playlist-service/app-service) project folder.
2. Additionally, you can use ```java -jar build/libs/<service>.jar --server.port=<port>``` if you'd like to use a different port.

# Link to the presentation video

**Link:** https://youtu.be/eijgE_N2uLM
