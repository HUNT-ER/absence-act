# Absence Act API
This API allows you to do CRUD operations to absense acts for employees

# Build with
## Backend
- Spring Boot
- Spring Data JDBC
- Spring Web MVC
- PostgreSQL
- Lombok

## Frontend
- HTML
- CSS
- JavaScript


## Set up 
- [*clone*](https://github.com/HUNT-ER/absence-act.git) the project
- change [src/main/resources/application.properties) file based on your database configurations
- run the project using [AbsenceEmployeeSystemApplication.java](src/main/java/com/boldyrev/absence_employee_system/AbsenceEmployeeSystemApplication.java) 

# API Reference 

## Ingredient categories operations

**GET** `/api/absense-act`
  returns list of absence acts

**GET** `/api/absense-act/{id}`
  returns absence act by ID


**POST** `/api/absense-act`
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

**PUT** `/api/absense-act`
updates absense-act by request body and id

**DELETE** `/api/absense-act/{id} `
deletes absense-act by id

# Entity diagram
![t_absence_act](https://github.com/HUNT-ER/absence-act/assets/38404914/0a15c891-c79b-4639-a74c-26c86f1a3ca1)

# What I learned
- Improved skills in Spring Boot, Spring Data JDBC, Html, CSS, JavaScript