# Logistic web service

Backend used for processing all related processes

Used technologies:

- IDE: IntelliJ IDEA
- AS: Tomcat 9
- DB: MySQL 5.7
- Maven: 3.5.3
- JPA: 2.0
- Spring Framework
- JSP

## Usage:
Deploy on local tomcat with __tomcat7:deploy__ goal

tomcat url: http://localhost:8080/manager/text, credentials are from local maven's settings.xml

Launch sonar with __sonar:sonar__ maven goal

sonar url: http://192.168.1.100:9000

## 2 kinds of API:

<server>/ - html view

<server>/api - rest view







## Rest api

<server>/api - root

<server>/api/trucks - trucks api

Tbd: authentification, pagination, better error handling

Reference: https://docs.apigee.com/api-baas/get-started/app-services-data-model-1



## /api/trucks

<server>/api/trucks/ - hello there

### GET

<server>/api/trucks - list of all trucks

<server>/api/trucks/{id} - get truck by its id 200:ok/404:not found

<server>/api/trucks/search?minCapacityKg=<weight> - get list of Trucks by minimal required capacity. 200:ok/

<server>/api/trucks/search?minCapacityKg=<weight>&resultSize=<size> - search with optional parameter resultSize(default = 1) - specifyed size of collection in callback



### POST 

<server>/api/trucks/ - post new truck in format JSON:

```json
{
  "registerNumber": "7PPDUBAF",
  "shiftSize": 2,
  "capacity": 20,
  "condition": "new",
  "currentCity": "Saint-Petersburg"
}
```

Success: 201 - created

Failed: 406 - not acceptable



### PUT

<server>/api/trucks/{id} - put here updated truck info in json - format see POST

Success: 202 - accepted

Failure: truck by id not found: 400 – Bad Request



```Curl
curl -X POST -H "Content-Type: application/json" -d '{"registerNumber": "7PPP12CF","shiftSize": 2,"capacity": 20,"condition": "new","currentCity": "Saint-Petersburg"}' "http://localhost:8090/api/trucks/" 
```




### DELETE

<server>/api/trucks/{id} - delete truck by requested id

Success: 200 - OK

Failure: 404 – Not Found



```
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
@Column(name = "id")
private long id;

@Column(name = "firstName")
private String firstName;

@Column(name = "surname")
private String surname;

@Column(name = "registrationNumber", unique = true)
private int registrationNumber;

@Column(name = "hoursInCurrentMonthWorks")
private int hoursInCurrentMonthWorks;

@Column(name = "status")
private String status;

@Column(name = "currentCity")
private String currentCity;

@Column(name = "currentTruck")
private String currentTruck;
```

