# User-Management App 🧑‍💻

#### Hi Dear 🖐️, In this app we have two microservices, **user microservice** and **Notification microservice**. When a event like register, login, update user or update password is triggered, a message is sent to *Notification microservice* with information about the respective action and stored in database.

### 💡 User Microservice

#### Unblocked Endpoints 🔓

Well, to register a user, we must send a payload to the request body as shown below:<br>
URL: `http://host:8000/v1/users`
~~~~
{
    "firstName": "Jhon",
    "lastName": "Doe",
    "cpf": "000.000.000-00",
    "birthdate": "2012-12-12",
    "email": "jhondoe@email.com",
    "password": "123456",
    "active": true
}
~~~~
> [!IMPORTANT]<br>
> Password field only accept more than six characteres

To login endpoint, we must send **Email** and **Password** in a payload like this:<br>
URL: `http://host:8000/v1/login`
~~~~
{
    "email": "jhondoe@email.com",
    "password": "123456",
}
~~~~
> [!NOTE]<br>
> If the credentials are correctly validated, user will receive `JWT Token` for unblock any other endpoint.

#### Blocked Endpoints 🔒

Get user by Id: <br>
URL: `http://host:8000/v1/users/{id}`

You must change `{id}` To user id and if user Exists, will receive a payload like this:
 ~~~~
{
    "firstName": "Jhon",
    "lastName": "Doe",
    "cpf": "000.000.000-00",
    "birthdate": "12-12-2012",
    "email": "jhondoe@email.com",
    "password": "7c4a8d09ca3762af61e59520943dc26494f8941b",
    "active": true
}
~~~~

Update user by Id: <br>
>[!NOTE]<br>
> We do not need to send the password or active fields, because password has its own endpoint.

So, the payload that will be sent will be like the one below: <br>
URL: `http://host:8000/v1/users/{id}`


 ~~~~
{
    "firstName": "Jhon",
    "lastName": "Doe",
    "cpf": "000.000.000-00",
    "birthdate": "12-12-2012",
    "email": "jhondoe@email.com",
}
~~~~

Update password by Id: <br>

URL: `http://host:8000/v1/users/{id}/password`

Payload body:

 ~~~~
{
    "password": "123456",
}
~~~~

### 💡 Notification Microservice

This microservice has only one route, the `GET Route`, and in it we can get all the events that were stored in the database.

URL: `http://host:8100/v1/notify`

Response payload:
~~~~
{
    "email": "jhondoe@email.com",
    "event": "LOGIN",
    "date": "10/09/2011"
}
~~~~

### Tools sed in this app 🔧:

![MongoDB](https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/Rabbitmq-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
