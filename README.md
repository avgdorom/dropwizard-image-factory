# dropwizard-image-factory
A dropwizard &amp; Spring &amp; Hibernate &amp; HSQLDB &amp; Mustache project for manipulating images.

**To run it you can:**
<ol>
<li>Clone -> mvn install -> java -jar target/ImageFactory-1.0-SNAPSHOT.jar server config.yml
<li>Configure the application in your IDE and run it.
</ol>

**To use it:**
<ol>
<li>The application runs on port 8080.
<li>There are 4 entry points:
    <ul>
        <li>http://localhost:8080/image/process (GET) - Process one image.<br/>
            Parameters: imageUrl (URL) and params (JSON).<br/>
            Example: http://localhost:8080/image/process?params={"resize":{"w":200,"h":500},"gray":{}}&imageUrl=http://www.estorilopen.net/wp-content/uploads/2016/05/tennis-1-1280x640.jpg
        <li>http://localhost:8080/image/demo (GET) - Process 4 predefined image URLs. Serves as a demo session.
            Parameters: params (JSON).<br/>
            Example: http://localhost:8080/image/process?params={"resize":{"w":200,"h":500},"gray":{}}
        <li>http://localhost:8080/image/findAll (GET) - Get a view of the images processed so far.<br/>
            Parameters: none.<br/>
            Example: http://localhost:8080/image/findAll
        <li>http://localhost:8080/image/{imageName} (GET) - Retrieves an image by the name stated in the URL.<br/>
            Parameters: imageName (string, in the URL path).<br/>
            Example: http://localhost:8080/image/myImage.jpg
    </ul>
</ol>

**To add more image manipulations:**
<ol>
<li>Add a new class, implementing ImageManipulator.
<li>Its implementation for getName() will determine the way to use it in the API.
<li>For example: If your new class' getName() method returns "crop", the a request using it may look like:<br/>
    http://localhost:8080/image/process?params={"crop":{"param1":value1,"param2":value2}}
</ol>
