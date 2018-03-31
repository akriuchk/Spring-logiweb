## Logistic web service

Backend used for processing all related processes

Used technologies:

- IDE: IntelliJ IDEA
- AS: Tomcat 9
- DB: MySQL 5.7
- Maven: 3.5.3
- JPA: 2.0
- Spring Framework
- JSP

Deploy on local tomcat with tomcat7:deploy

## 2 kinds of API:

<server>/ - html view

<server>/api - rest view



## Rest api

/api - root

/api/trucks/ - list of all trucks



#/api/trucks

"" - hello there

GET  / - list of all trucks

POST / - post new truck in format JSON:

```json
{"registerNumber": "7PPDUBAF","shiftSize": 2,"capacity": 20,"condition": "new","currentCity": "Saint-Petersburg"}
```

Success: 201 - created

Failed: 406 - not acceptable