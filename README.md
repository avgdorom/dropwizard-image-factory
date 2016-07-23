# dropwizard-image-factory
A dropwizard &amp; Spring &amp; Hibernate &amp; HSQLDB &amp; Mustache project for manipulating images.

**To run it you can:<br/>**
1. <p>Clone -> mvn install -> java -jar target/ImageFactory-1.0-SNAPSHOT.jar<br/></p>
2. <p>Configure the application in your IDE and run it.</p>

**To use it:<br/>**
1. The application runs on port 8080.<br/>
2. There are 4 entry points:<br/>
    a. <p>http://localhost:8080/image/process (GET) - Process one image.<br/>
       Parameters: imageUrl (URL) and params (JSON).<br/>
       Example: http://localhost:8080/image/process?params={"resize":{"w":200,"h":500},"gray":{}}&imageUrl=http://www.estorilopen.net/wp-content/uploads/2016/05/tennis-1-1280x640.jpg<br/></p>
    b. <p>http://localhost:8080/image/demo (GET) - Process 4 predefined image URLs. Serves as a demo session.<br/>
       Parameters: params (JSON).<br/>
       Example: http://localhost:8080/image/process?params={"resize":{"w":200,"h":500},"gray":{}}<br/></p>
    c. <p>http://localhost:8080/image/findAll (GET) - Get a view of the images processed so far.<br/>
       Parameters: none.<br/>
       Example: http://localhost:8080/image/findAll<br/></p>
    d. <p>http://localhost:8080/image/{imageName} (GET) - Retrieves an image by the name stated in the URL.<br/>
       Parameters: imageName (string, in the URL path).<br/>
       Example: http://localhost:8080/image/myImage.jpg</p>

**To add more image manipulations:<br/>**
1. <p>Add a new class, implementing ImageManipulator.<br/></p>
2. <p>Its implementation for getName() will determine the way to use it in the API.<br/></p>
3. <p>For example: If your new class' getName() method return "crop", the a request using it may look like:<br/>
    http://localhost:8080/image/process?params={"crop":{"param1":value1,"param2":value2}}</p>   
