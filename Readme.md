# Spring Security 6 Example

I have struggled to find a working example of [Spring Security 6](https://spring.io/projects/spring-security).

Finally, I found this example by [@phegondev](https://github.com/phegondev) which actually works, contrary
to the other examples I found on internet.

So I forked it.

The first change was to use PostgreSQL instead of MySQL and running it in a Docker 
container. See [start_db.sh](start_db.sh) script for details.

## About the example

The example has two entities, User and Product. 

The [Controller](src/main/java/se/artcomputer/edu/security6/controller/Controller.java)
has annotations that restrict access to the endpoints, based
on roles. A user with role `ADMIN` list all users, while a user with role 
`USER` can only see their own user.

## V2 a custom login form
In v2, a custom login form was added. Since I use static resources, I had to add an endpoint to redirect to the form, 
else it simply didn't work. As you can see in the `SecurityConfig` class, I use `formLogin` configuration with the 
endpoint /`mylogin`. When the form was in static resources, nothing happened on submit, it just came back to the form. 
But with the endpoint that redirects to `/mylogin-form.html`, it worked.

## V3 logout and Flyway migration
In v3, I added a logout endpoint and Flyway migration. The logout endpoint is `/logout` and found on the index page.
Spring Security has a default logout endpoint, so nothing special was needed. 

Flyway is used to set up an empty database with a schema. The database is populated with a user with role `USER` 
and a user with role `ADMIN`. The password is `user` and `admin`, respectively. It also populates the database with
a product. See [V3__Initial_setup.sql](src/main/resources/db/migration/V1__Initial_setup.sql).

## Running the example

1. Start your Docker engine.
2. Run the script `start_db.sh` to start a PostgreSQL container. It will
download the image if you don't have it.
3. Run the Spring Boot application. See [SpringSecurityUpdatedApplication](src/main/java/se/artcomputer/edu/security6/SpringSecurityUpdatedApplication.java).
It will start the application on port [3030](http://localhost:3030).
4. Click on the `My Page` link. You should see a login form.
5. Log in with the user you created.

