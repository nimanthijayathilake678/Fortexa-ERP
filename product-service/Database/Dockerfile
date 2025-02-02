# Use official MongoDB image as base image
FROM mongo:7.0.5

# Set environment variables

 ENV MONGO_INITDB_ROOT_USERNAME: ${MONGO_INITDB_ROOT_USERNAME}
 ENV MONGO_INITDB_ROOT_PASSWORD: ${MONGO_INITDB_ROOT_PASSWORD}
 ENV MONGO_INITDB_DATABASE: ${MONGO_INITDB_DATABASE}

# Set the working directory
WORKDIR /data

# Expose default MongoDB port
EXPOSE 27017

# Mount volume for persistent data storage
VOLUME /data/db

# Run MongoDB as a background process with the custom config file if necessary
CMD ["mongod", "--auth"]
