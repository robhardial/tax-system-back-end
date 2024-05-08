# Use OpenJDK as the base image
FROM openjdk:20-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the packaged jar file into the container
COPY target/taxfiling-0.0.1-SNAPSHOT.jar /app/taxfiling-0.0.1-SNAPSHOT.jar 

# Expose the port your app runs on
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "/app/taxfiling-0.0.1-SNAPSHOT.jar"]

