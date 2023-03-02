# Gift Certificates API #

<img src="https://user-images.githubusercontent.com/100201504/218796290-96c0747d-1c3f-4d9b-8641-138aafe9b82f.png" width="300" alt=""/>

this API allows you to create gift certificates and tags

once you run the project certificates and tags will be available 

at `http://localhost:8080/certificates` and `http://localhost:8080/tags` respectively


# Set up #

- [*clone*](https://github.com/bakhridinova/gift-certificates.git) the project
- change project [*SQL dialects*](https://www.jetbrains.com/help/idea/settings-languages-sql-dialects.html) for the following [*PostgreSQL*](https://www.postgresql.org/about/) for [`main`](repository/src) directory
- create new postgresql database using ddl.sql and dml.sql in the [`database`](repository/src/main/resources/database) directory
- change [`application.properties`](repository/src/main/resources/application.properties) file based on your database configurations and add new [*Tomcat 10*](https://tomcat.apache.org/tomcat-11.0-doc/introduction.html) configuration to the project
- download [*Postman*](https://www.postman.com/home) in case you do not have it installed yet and follow the installation steps
- download [*WSL2*](https://learn.microsoft.com/en-us/windows/wsl/install) and [*Docker*](https://www.docker.com/get-started/) in case you don't have them installed and make sure that docker is running on your machine
- now you are ready to go :3


# Allowed operations #

## Certificate operations ##

### Get all certificates ###
GET `/certificates`

returns list of certificates 


### Get one certificate ###
GET `/certificates/{id}`

returns certificate if id is valid, otherwise detailed error message


### Get certificates with tags ###
POST `/certificates/tag`

the request body needs to be in JSON format array and include the following properties:

- name - String (*required*)

returns list of certificates with given tags if all properties are valid, otherwise detailed error message

```
[
    {
        "name": "text between 3 and 30 characters"
    },
    ...
]
```


### Get certificates by search filter ###
POST `/certificates/search`

the request body needs to be in JSON format and include the following properties:

- searchValue - String (*required*)
- searchType - String (*required*) 
- searchPlace - String (*required*) 

returns list of certificates found by given search value/type/place if all properties are valid, otherwise detailed error message

```
{
    "searchValue": "text between 3 and 30 characters",
    "searchType": "name or description",
    "searchPlace": "begins_with, contains or ends_with"
}
```

### Get certificates by sort filter ###
POST `/certificates/sort`

the request body needs to be in JSON format and include the following properties:

- sortType - String (*required*)
- sortOrder - String (*required*) 

returns list of certificates sorted by given sort type/order if all properties are valid, otherwise detailed error message

```
{
    "sortType": "name or create_date or last_update_date",
    "sortOrder": "asc or desc"
}
```


### Create certificate ###
POST `/certificates`

the request body needs to be in JSON format and include the following properties:

- name - String (*required*)
- description - String (*required*)
- price - Double (*required*)
- duration - Integer (*required*)
- tags - Array (*not required*)

creates new certificate if all passed properties are valid, otherwise detailed error message

```
{
    "name": "text between 4 and 40 characters",
    "description": "text between 8 and 80 characters",
    "price": "between 2.0 and 20.0",
    "duration": "between 1 and 60",
    "tags": []
}
```


### Edit certificate ###
PATCH `/certificates`

the request body needs to be in JSON format and include the following properties:

- id - Long (*required*)
- name - String (*not required*)
- description - String (*not required*)
- price - Double (*not required*)
- duration - Integer (*not required*)
- tags - Array (*not required*)

edits certificate if all passed properties are valid, otherwise detailed message

```
{
    "id" : "id",
    "name": "text between 4 and 40 characters",
    "description": "text between 8 and 80 characters",
    "price": "between 2.0 and 20.0",
    "duration": "between 1 and 60",
    "tags": [
        {
            "name": "text between 3 and 30 characters"
        }
    ]
}
```

### Delete certificate ###
DELETE `/certificates/{id}`

deletes certificate if id is valid, otherwise detailed error message 


## Tag Operations ##

### Get all tags ###
GET `/tags`

returns list of tags

### Get one tag ###
GET `/tags/{id}`

returns tag if id is valid, otherwise detailed error message

### Create certificate ###
POST `/tags`

the request body needs to be in JSON format and include the following property:

- name - String (*required*)

creates new tag if passed property is valid, otherwise detailed error message

```
{
    "name": "text between 3 and 30 characters",
}
```

### Delete certificate ###
DELETE `/tags/{id}`

deletes tag if id is valid, otherwise detailed error message 



# Property constrain tables #

## Certificate properties ##

| property       | data type | null | empty | constrain                           |
|----------------|-----------|:----:|:-----:|-------------------------------------|
| id             | Long      |  +   |   +   | format: number                      |
| name           | String    |  -   |   -   | min length: 4, max length: 40       |
| description    | String    |  -   |   -   | min length: 8, max length: 80       |
| price          | Double    |  -   |   -   | min value: 2.0, max value: 20.0     |
| duration       | Integer   |  -   |   -   | min value: 1, max value: 60         |
| createDate     | String    |  -   |   -   | format: ISO 8601                    |
| lastUpdateDate | String    |  -   |   -   | format: ISO 8601                    |
| tags           | Array     |  -   |   +   | either empty array or array of tags |

## Tag properties ##

| property | data type | null | empty | constrain                     |
|:---------|:----------|:----:|:-----:|:------------------------------|
| id       | Long      |  +   |   +   | -                             |
| name     | String    |  -   |   -   | min length: 3, max length: 30 |