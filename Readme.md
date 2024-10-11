# Spring Security 6 Example

![Build Status](https://github.com/perty/springboot-security-6/actions/workflows/maven.yml/badge.svg)

I have struggled to find a working example of [Spring Security 6](https://spring.io/projects/spring-security).

Finally, I found this example by [@phegondev](https://github.com/phegondev) which actually works, contrary to the other
examples I found on internet.

So I forked it.

The first change was to use PostgreSQL instead of MySQL and running it in a Docker container.
See [start_db.sh](start_db.sh) script for details.

The changes are marked with version tags. This is so that you can understand it
peace by peace.

## About the example

The example has two entities, User and Product.

The [Controller](src/main/java/se/artcomputer/edu/security6/controller/Controller.java)
has annotations that restrict access to the endpoints, based on roles. A user with role `ADMIN` may list all users,
while
a user with role `USER` can only see their own information.

## V2 a custom login form

In v2, a custom login form was added instead of relying on the browser's prompt. Since I use static resources (not
thymeleaf), I had to add an endpoint to redirect to the form, else it simply didn't work. As you can see in the
[SecurityConfig](src/main/java/se/artcomputer/edu/security6/config/SecurityConfig.java) class, I use `formLogin`
configuration with the endpoint /`mylogin`.

When the form was in static resources, nothing happened on submit, it just came back to the form. But with the endpoint
that redirects to `/mylogin-form.html`, it worked.

## V3 logout and Flyway migration

In v3, I added a logout endpoint and [Flyway](https://www.red-gate.com/products/flyway/community) migration.

The logout endpoint is `/logout` and reached from the index page. Spring Security has a default logout endpoint, so
nothing special was needed.

Flyway is used to set up an empty database with a schema. The database is populated with a user with role `USER`
and a user with role `ADMIN`. The password is `user` and `admin`, respectively. It also populates the database with
a product. See [V3__Initial_setup.sql](src/main/resources/db/migration/V1__Initial_setup.sql).

## V4 user management

In v4, I added user management. Users can change their password and administrators can add new users and reset
passwords. The admin page is reached from the administrator's profile page. If you have the role `ADMIN`, you will see
a button that link to the admin page.

The endpoints are protected with roles, eg '/users/reset-password' is protected with the role `ADMIN`.

## Running the example

1. Start your Docker engine.
2. Run the script `start_db.sh` to start a PostgreSQL container. It will
   download the image if you don't have it.
3. Run the Spring Boot application.
   See [SpringSecurityApplication](src/main/java/se/artcomputer/edu/security6/SpringSecurityApplication.java).
   It will start the application on port [3030](http://localhost:3030).
4. Click on the `My Page` link. You should see a login form.
5. Log in with user `admin@a.b` and password `admin`.

