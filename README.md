<h1>🚌 Online Bus Booking System – Documentation</h1>

<p>This is a full-stack web application for booking bus tickets online. The system manages routes, schedules, passenger information, and ticketing using Spring Boot and MySQL.</p>

<hr/>

<h2>📘 1. Introduction</h2>
<p>
  This document provides a comprehensive guide on setting up, configuring, and running the Online Bus Booking System. It includes:
</p>
<ul>
  <li>📌 API endpoint documentation</li>
  <li>📌 Request and response format details</li>
  <li>📌 Data validation rules</li>
</ul>

<hr/>

<h2>⚙️ 2. Project Setup</h2>

<h3>🧰 Prerequisites</h3>
<p>Ensure you have the following installed on your system:</p>
<ul>
  <li>☕ Java 21 or later</li>
  <li>📦 Maven</li>
  <li>🗄️ MySQL or any other preferred relational database</li>
</ul>

<h3>📥 Steps to Run the Application</h3>
<ol>
  <li><b>Clone the repository:</b><br/>
    <code>git clone &lt;repo_url&gt;</code>
  </li><br/>

  <li><b>Navigate to the project directory:</b><br/>
    <code>cd demoBus</code>
  </li><br/>

  <li><b>Build the project:</b><br/>
    <code>mvn clean install</code>
  </li><br/>

  <li><b>Configure database settings in <code>application.properties</code>:</b><br/>
    <pre>
spring.datasource.url=jdbc:mysql://localhost:3306/demoBus
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
    </pre>
  </li>

  <li><b>Run the application:</b><br/>
    <code>mvn spring-boot:run</code>
  </li>
</ol>

<hr/>

<h2>🛠️ 3. Configuration Details</h2>

<h3>🗄️ Database Configuration</h3>
<p>
  The application uses <strong>MySQL</strong> as the default relational database. Ensure the database <code>demoBus</code> exists, or create it manually before running the app.
</p>

<h4><code>application.properties</code> Configuration:</h4>
<pre>
spring.datasource.url=jdbc:mysql://localhost:3306/demoBus
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
</pre>

<hr/>

<h2>📮 4. API & Features (Coming Next)</h2>
<ul>
  <li>🚌 Bus search and booking</li>
  <li>📅 Schedule and availability check</li>
  <li>🎫 Ticket confirmation and cancellation</li>
  <li>🧾 JSON request & response samples</li>
  <li>🧪 Unit & integration testing approach</li>
</ul>

<hr/>

