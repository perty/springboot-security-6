# Spring Security 6 Example

I have struggled to find a working example of Spring Security 6.

Finally, I found this example by [@phegondev](https://github.com/phegondev) which actually works, contrary
to the other examples I found on internet.

So I forked it.

The first change was to use PostgreSQL instead of MySQL and running it in a Docker 
container. See [start_db.sh](start_db.sh) script for details.

## About the example

The example has two entities, User and Product. 

The [Controller](src%2Fmain%2Fjava%2Fcom%2FspringSecurityUpdated%2FspringSecurityUpdated%2Fcontroller%2FController.java)
has annotations that restrict access to the endpoints, based
on roles. A user with role `ADMIN` list all users, while a user with role 
`USER` can on see their own user.

## Running the example

1. Start your Docker engine.
2. Run the script `start_db.sh` to start a PostgreSQL container. It will
download the image if you don't have it.
3. Run the Spring Boot application. See [SpringSecurityUpdatedApplication](src%2Fmain%2Fjava%2Fcom%2FspringSecurityUpdated%2FspringSecurityUpdated%2FSpringSecurityUpdatedApplication.java).
4. Create a user by using the endpoint `POST /user/save` with a JSON body like
    ```json
    {
      "email": "admin@a.b",
      "password": "admin",
      "roles": "USER,ADMIN"
    }
    ```
5. Access a protected endpoint, like `/user/all`. You should see a login form [*](#defaults).
6. Logout using `/login?logout` endpoint. [*](#defaults)

#### Defaults
Spring Security comes with some defaults. Like how you logout and the login form.
