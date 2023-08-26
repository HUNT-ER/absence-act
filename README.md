# Absence Act API
This API allows you to do CRUD operations to absense acts for employees

# Build with
## Backend
- Spring Boot
- Spring Data JDBC
- Spring Web MVC
- PostgreSQL
- Lombok
- Testcontainers

## Frontend
- HTML
- CSS
- JavaScript


## Set up 
- [*clone*](https://github.com/HUNT-ER/absence-act.git) the project
- change [src/main/resources/application.properties) file based on your database configurations
- run the project using [AbsenceEmployeeSystemApplication.java](src/main/java/com/boldyrev/absence_employee_system/AbsenceEmployeeSystemApplication.java)
- also you can run docker-compose.yml file with next command to run project in docker
```agsl
  docker compose up -d --build
```
- web content will be available on "localhost:8081" page

# View

## Page with all acts
![image](https://github.com/HUNT-ER/absence-act/assets/38404914/16b1d067-84f1-47f1-aa01-82079c613861)

## Page with creating/editing act
![image](https://github.com/HUNT-ER/absence-act/assets/38404914/57453056-3ea9-4b14-9784-bbe29e4a0aad)


# API Reference 

## Ingredient categories operations

**GET** `/api/absense-acts`
  returns list of absence acts

**GET** `/api/absense-acts/{id}`
  returns absence act by ID


**POST** `/api/absense-acts`
  create new absence act by request body:
```agsl
  request body:
    {
        "reason": "",
        "start_date": "",
        "duration": 1,
        "discounted": false,
        "description": ""
    }
```

**PUT** `/api/absense-acts`
updates absense-act by request body and id

**DELETE** `/api/absense-acts/{id} `
deletes absense-act by id

# Entity diagram
![t_absence_act](https://github.com/HUNT-ER/absence-act/assets/38404914/0a15c891-c79b-4639-a74c-26c86f1a3ca1)

# What I learned
- Improved skills in Spring Boot, Spring Data JDBC, Html, CSS, JavaScript
