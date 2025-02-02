<h1>Product Service - Clothing Shop Microservice</h1> 
<p>The <strong>Product Service</strong> is a microservice responsible for handling all product-related operations in the clothing shop application. It is a part of the larger e-commerce system and interacts with other services such as Order Service, Payment Service, and Inventory Service. The service is built using <strong>Spring Boot</strong> and is configured to interact with <strong>MongoDB</strong> for product data storage.</p>

<h2>Features</h2>
<ul>
     <li><strong>Product Management</strong>: Handle product CRUD (Create, Read, Update, Delete) operations.</li>
    <li><strong>MongoDB Integration</strong>: Store product data using MongoDB.</li>
    <li><strong>Error Handling</strong>: Utilize global exception handling with descriptive error messages.</li>
    <li><strong>DTO Usage</strong>: Use Data Transfer Objects (DTOs) for API communication.</li>
    <li><strong>Security</strong>: Basic security setup with JWT authentication.</li>
    <li><strong>API Documentation</strong>: Swagger-based documentation for all exposed endpoints.</li>
</ul>

<h2>Prerequisites</h2>
<p>Before running the project, ensure you have the following software installed:</p>
<ul>
     <li><strong>Java 11+</strong></li>
     <li><strong>Maven</strong> or <strong>Gradle</strong></li>
     <li><strong>MongoDB</strong> (local or cloud instance)</li>
</ul>

<h2>Project Structure</h2>
<pre>
       will be updated.
</pre>

<h2>Getting Started</h2>
<h3>1. Clone the Repository</h3>
<pre>
     git clone https://github.com/yourusername/product-service.git
     cd product-service
</pre>

<h3>2. Configure MongoDB</h3>
<p>Make sure you have MongoDB running locally or use a cloud-based service like <strong>MongoDB Atlas</strong>. If you're running it locally, you can configure it in the <code>application.properties</code> file:</p>
<pre>
     spring.data.mongodb.uri=mongodb://localhost:27017/clothing-shop
</pre>
<p>For MongoDB Atlas, use the connection string provided by Atlas.</p>

<h3>3. Run the Service</h3>
<p>To run the service locally, execute the following Maven command:</p>
<pre>
     mvn spring-boot:run
</pre>
<p>The service will be running on <code>http://localhost:8080</code> by default.</p>

<h3>4. API Documentation</h3>
<p>Once the service is running, you can view the API documentation via <strong>Swagger UI</strong> at:</p>
<pre>
     http://localhost:8080/swagger-ui.html
</pre>

<h3>5. Endpoints</h3>
<p>Here are some of the key API endpoints:</p>
<ul>
     <li><strong>GET</strong> <code>/api/products</code>: Retrieve all products.</li>
     <li><strong>GET</strong> <code>/api/products/{id}</code>: Retrieve a single product by ID.</li>
     <li><strong>POST</strong> <code>/api/products</code>: Create a new product.</li>
     <li><strong>PUT</strong> <code>/api/products/{id}</code>: Update an existing product.</li>
     <li><strong>DELETE</strong> <code>/api/products/{id}</code>: Delete a product by ID.</li>
</ul>

<h3>6. Error Handling</h3>
<p>The service uses a global exception handler to manage errors consistently. If an error occurs (e.g., a product is not found), the response will return an error code and a descriptive message.</p>
<pre>
     {
     "error": "Product with ID 123 not found."
     }
</pre>

<h3>7. Security</h3>
<p>The service is configured with basic JWT authentication. You'll need to implement authentication endpoints and token management if needed for production use.</p>

<h2>Technologies Used</h2>
<ul>
     <li><strong>Spring Boot</strong>: For building the microservice.</li>
     <li><strong>MongoDB</strong>: NoSQL database for storing product data.</li>
     <li><strong>Swagger</strong>: For generating API documentation.</li>
     <li><strong>DTO (Data Transfer Object)</strong>: For managing API responses and requests.</li>
     <li><strong>Exception Handling</strong>: Custom global exception handler for consistent error messages.</li>
 </ul>

<h2>Contributing</h2>
<ol>
     <li>Fork the repository.</li>
     <li>Create a new branch (<code>git checkout -b feature/your-feature</code>).</li>
     <li>Commit your changes (<code>git commit -am 'Add new feature'</code>).</li>
     <li>Push to the branch (<code>git push origin feature/your-feature</code>).</li>
     <li>Open a Pull Request.</li>
</ol>

<h2>License</h2>
<p>This project is licensed under the MIT License - see the <a href="LICENSE">LICENSE</a> file for details.</p>

<h2>Acknowledgments</h2>
<ul>
     <li><strong>Spring Boot</strong> for the framework.</li>
     <li><strong>MongoDB</strong> for the database solution.</li>
     <li><strong>Swagger</strong> for easy API documentation.</li>
</ul>
