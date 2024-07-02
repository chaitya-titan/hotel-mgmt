# Coding StayEase Application

## Description

* This is a Hotel Booking and Management System backend application built with Spring Boot and Spring Data JPA using MySQL. It provides RESTful APIs for creating, reading, updating, and deleting Hotel and Booking them.

## Running the Application

#### Just use run on LearningSystemApplication if on IntelliJ IDEA or use the following command if using Gradle

```bash
./gradlew bootRun
```

## Database

### MySQL

#### If you don't have mysql installed, run the following command
```bash
brew install mysql
mysql --version
```

#### To Login to MySQL

```bash
brew services start mysql
mysql
mysql -u root -p
CREATE DATABASE <your-database-name>;
```

#### Add this in application.properties
```
spring.application.name=learning_system
spring.datasource.url=jdbc:mysql://localhost:3306/stayease
spring.datasource.username=root
spring.datasource.password=<your-password>
spring.jpa.hibernate.ddl-auto=update

server.port=8080
```



## API Endpoints

### Hotel API

### `GET /hotels/{id}`
Retrieve the details of a specific hotel

- **Parameters**:
    - `id` (path): ID of the hotel to retrieve

- **Response**:
    - **Status**: `200 OK`
    - **Body**:
        ```json
        {
            "id": "string",
            "hotelName": "string",
            "location": "string",
            "description": "string",
            "rooms": [
                {
                    "id": "string",
                    "roomStatus": "string",
                    "price": "number",
                    ...
                },
                ...
            ],
            ...
        }
        ```

### `GET /hotels`
Retrieve a list of all hotels

- **Response**:
    - **Status**: `200 OK`
    - **Body**:
        ```json
        [
            {
                "id": "string",
                "hotelName": "string",
                "location": "string",
                "description": "string",
                "rooms": [
                    {
                        "id": "string",
                        "roomStatus": "string",
                        "price": "number",
                        ...
                    },
                    ...
                ],
                ...
            },
            ...
        ]
        ```

### `POST /hotels/create`
Create a new hotel

- **Request Body**:
    ```json
    {
        "hotelName": "string",
        "location": "string",
        "description": "string",
        ...
    }
    ```
- **Response**:
    - **Status**: `201 Created`
    - **Body**:
        ```json
        {
            "id": "string",
            "hotelName": "string",
            "location": "string",
            "description": "string",
            ...
        }
        ```

### `PUT /hotels/{id}`
Update the details of a specific hotel

- **Parameters**:
    - `id` (path): ID of the hotel to update

- **Request Body**:
    ```json
    {
        "hotelName": "string",
        "location": "string",
        "description": "string",
        ...
    }
    ```

- **Response**:
    - **Status**: `200 OK`
    - **Body**:
        ```json
        {
            "id": "string",
            "hotelName": "string",
            "location": "string",
            "description": "string",
            ...
        }
        ```

### `DELETE /hotels/{id}`
Delete a specific hotel

- **Parameters**:
    - `id` (path): ID of the hotel to delete

- **Response**:
    - **Status**: `204 No Content`

### `POST /hotels/{hotelId}/book`
Create a booking for a hotel

- **Parameters**:
    - `hotelId` (path): ID of the hotel to book

- **Response**:
    - **Status**: `201 Created`
    - **Body**:
        ```json
        {
            "bookingId": "string",
            "userId": "string",
            "hotelId": "string",
            "roomId": "string",
            "checkInDate": "string",
            "checkOutDate": "string",
            ...
        }
        ```

### User API

### `POST /users/register`
Register a new user

- **Request Body**:
    ```json
    {
        "username": "string",
        "email": "string",
        "password": "string",
        ...
    }
    ```
- **Response**:
    - **Status**: `201 Created`
    - **Body**:
        ```json
        {
            "id": "string",
            "username": "string",
            "email": "string",
            ...
        }
        ```

### `POST /users/admin/register`
Register a new admin user

- **Request Body**:
    ```json
    {
        "username": "string",
        "email": "string",
        "password": "string",
        ...
    }
    ```
- **Response**:
    - **Status**: `201 Created`
    - **Body**:
        ```json
        {
            "id": "string",
            "username": "string",
            "email": "string",
            ...
        }
        ```

### `POST /users/login`
Login a user

- **Request Body**:
    ```json
    {
        "email": "string",
        "password": "string"
    }
    ```
- **Response**:
    - **Status**: `200 OK`
    - **Body**:
        ```json
        {
            "token": "string",
            "userId": "string",
            ...
        }
        ```


### Booking API

### `GET /bookings`
Retrieve all bookings

- **Response**:
    - **Status**: `200 OK`
    - **Body**:
        ```json
        [
            {
                "bookingId": "string",
                "user": {
                    "userId": "string",
                    "username": "string",
                    ...
                },
                "hotel": {
                    "hotelId": "string",
                    "hotelName": "string",
                    "location": "string",
                    ...
                },
                "room": {
                    "roomId": "string",
                    "roomNumber": "string",
                    "price": "number",
                    ...
                },
                "checkInDate": "string",
                "checkOutDate": "string"
            },
            ...
        ]
        ```

### `POST /bookings/check-out/{bookingId}`
Check out from a booking

- **Parameters**:
    - `bookingId` (path): ID of the booking to check out

- **Response**:
    - **Status**: `200 OK`
    - **Body**:
        ```json
        {
            "bookingId": "string",
            "user": {
                "userId": "string",
                "username": "string",
                ...
            },
            "hotel": {
                "hotelId": "string",
                "hotelName": "string",
                "location": "string",
                ...
            },
            "room": {
                "roomId": "string",
                "roomNumber": "string",
                "price": "number",
                ...
            },
            "checkInDate": "string",
            "checkOutDate": "string"
        }
        ```

### `DELETE /bookings/{bookingId}`
Delete a specific booking

- **Parameters**:
    - `bookingId` (path): ID of the booking to delete

- **Response**:
    - **Status**: `200 OK`
    - **Body**:
        ```json
        {
            "message": "Booking deleted successfully"
        }
        ```



## Postman API

#### A Postman collection for testing the API is available [here](https://api.postman.com/collections/20879467-483236e4-0739-4c00-b1e8-83ab7196024c?access_key=PMAT-01J1TBYC8JG7J261XEG9TRW15X).