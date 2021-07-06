#Processor Project

#Design
<br/>For this process the Facade pattern was implemented, so that the information that is entered from 
the controller layer never communicates with the service layer, this will allow that if changes are required 
the process is not coupled and any refactor can be done in an agile way.
<br/>
#Development strategy
In addition to the above described, the handling of the information in parallel is implied, 
due to the large volume of data to be processed, and a batch insertion, so that the database is not 
the blocking point when performing so many transactions.

<br/>For the management of parallel processing, the parallel stream API was used, since it manages the resources 
of the machine in the most optimal way.

<br/>For the batch insertion, the size of 1000 was added, given that the performance analysis found and from experience, 
the size of 1000 has been the most effective block insertion.
<br/>
#How to execute
<br/> For the execution of the project, a Postgres database must be created with schema (geolocation) user (postgres), 
and password (admin).
<br/>{ url=jdbc:postgresql://localhost:5432/geolocation, username=postgres, password=admin }
<br/>
For the file upload the url is http://localhost:8080/api/file/upload, the parameter name is file, and.
the value is a csv file.
<br/>
For the IP query, the url is http://localhost:8080/api/file/{ipAddress}/find
<br/>
If you want to check the endpoints created, you can check on the Swagger page: http://localhost:8080/swagger-ui