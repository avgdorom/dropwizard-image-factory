# dropwizard-image-factory
A dropwizard &amp; Spring &amp; Hibernate &amp; HSQLDB &amp; Mustache project for manipulating images

To run it you can:
1. Clone -> mvn install -> java -jar target/ImageFactory-1.0-SNAPSHOT.jar
2. Configure the application in your IDE and run it.

To use it:
1. The application runs on port 8080.
2. There are 4 entry points:
    a. http://localhost:8080/image/process (GET) - Process one image.
       Parameters: imageUrl (URL) and params (JSON).
       Example: http://localhost:8080/image/process?params={"resize":{"w":200,"h":500},"gray":{}}&imageUrl=http://www.estorilopen.net/wp-content/uploads/2016/05/tennis-1-1280x640.jpg
    b. http://localhost:8080/image/demo (GET) - Process 4 predefined image URLs. Serves as a demo session.
       Parameters: params (JSON).
       Example: http://localhost:8080/image/process?params={"resize":{"w":200,"h":500},"gray":{}}
    c. http://localhost:8080/image/findAll (GET) - Get a view of the images processed so far.
       Parameters: none.
       Example: http://localhost:8080/image/findAll
    d. http://localhost:8080/image/{imageName} (GET) - Retrieves an image by the name stated in the URL.
       Parameters: imageName (string, in the URL path).
       Example: http://localhost:8080/image/myImage.jpg

To add more image manipulations:
1. Add a new class, implementing ImageManipulator. 
2. Its implementation for getName() will determine the way to use it in the API.
3. For example: If your new class' getName() method return "crop", the a request using it may look like:
    http://localhost:8080/image/process?params={"crop":{"param1":value1,"param2":value2}}   
