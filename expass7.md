# Part 1
For part 1 I used the command 
```bash
docker pull postgres
```
to download the postgres docker image. Then I ran that image with the following arguments
```
docker run -p 5432:5432 -e POSTGRES_PASSWORD=mypassword -d --name my-postgres --rm postgres
```
and got the database running, but without any of the tables needed so the tests were failing when trying to run them.

Next I replaced the old connection parameters in the `persistence.xml` file with the following PostgreSQL connection parameters
```xml
    <property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQLDialect"/>
    <property name="hibernate.connection.driver_class" value="org.postgresql.Driver"/>
    <property name="hibernate.connection.url" value="jdbc:postgresql://127.0.0.1:5432/postgres"/>
    <property name="hibernate.connection.username" value="jpa_client"/>
    <property name="hibernate.connection.password" value="secret"/>
```
I also the following three lines to the .xml file to generate the sql schema, where `schema.up.sql` is the one I used to generate the schema when running the postgres database.
```xml
    <property name="jakarta.persistence.schema-generation.scripts.action" value="drop-and-create"/>
    <property name="jakarta.persistence.schema-generation.scripts.create-target" value="schema.up.sql"/>
    <property name="jakarta.persistence.schema-generation.scripts.drop-target" value="schema.down.sql"/>
```

After doing this and running the program again I got the .sql file I needed, and then ran docker with these arguments
```bash
docker run 
-p 5432:5432 
-e POSTGRES_USER=jpa_client 
-e POSTGRES_PASSWORD=secret 
-v (path to schema.up.sql directory):/docker-entrypoint-initdb.d/ 
-d --name my-postgres --rm postgres
```
and this generated the tables needed to make the tests pass.

# Part 2
When making the dockerfile I looked at the lecture notes for L14 and got some help from that, and ended up with the following `Dockerfile`
```dockerfile
# Do compilation inside a (throw-away) builder container
FROM gradle:8.8-jdk21 AS builder

# working directory inside the container is implicitly /home/gradle, but we make it explicit here
WORKDIR /home/gradle

# copy only necessary source code files
COPY settings.gradle.kts gradlew build.gradle.kts ./
# Copying a folder recursively need special attention
COPY src ./src
COPY gradle ./gradle

# Ensure gradlew has execute permissions
RUN chmod +x ./gradlew

# Convert line endings to Unix-style (LF)
RUN sed -i 's/\r$//' ./gradlew

# compile with gradle
RUN ./gradlew bootJar

# rename and move the resulting JAR file
RUN mv build/libs/demo-0.0.1-SNAPSHOT.jar app.jar


# This will be the base image for the running application
FROM eclipse-temurin:21-alpine

# creating a new user to avoid running the app as root
RUN addgroup -g 1000 app
RUN adduser -G app -D -u 1000 -h /app app

# switch into the newly created user and directory
USER app
WORKDIR /app

# copy the app from the builder image
COPY --from=builder --chown=1000:1000 /home/gradle/app.jar .

# Start the Spring Boot app when the container starts
CMD ["java", "-jar", "app.jar"]
```
This docker file first builds the application and then runs the generated `app.jar` file after it's done building. The reason for including the building part first
is because doing it this way will save us a lot of storage when building this dockerfile. I also ran into some problems when making this, as at first when building the
dockerfile it didn't recognize the `./gradlew` command. I found out after some time that it's because linux uses a different end of line character than windows, 
and when I added this line `RUN sed -i 's/\r$//' ./gradlew` to convert the line endings to unix-style line endings, I could build the dockerfile without any issues.

To run the dockerfile I just ran the command 
```bash
docker run -p 8080:8080 expass7
```
and the application started without any issues.