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
`USER` can only see their own user.

## V2
In v2, a custom login form was added. Since I use static resources, I had to add an endpoint to redirect to the form, else it simply didn't work. As you can see in the `SecurityConfig` class, I use `formLogin` configuration with the endpoint /`mylogin`. When the form was in static resources, nothing happened on submit, it just came back to the form. But with the endpoint that redirects to `/mylogin-form.html`, it worked.

## Running the example

1. Start your Docker engine.
2. Run the script `start_db.sh` to start a PostgreSQL container. It will
download the image if you don't have it.
3. Run the Spring Boot application. See [SpringSecurityUpdatedApplication](src%2Fmain%2Fjava%2Fcom%2FspringSecurityUpdated%2FspringSecurityUpdated%2FSpringSecurityUpdatedApplication.java).
It will start the application on port [3030](http://localhost:3030).
4. Create a user by using the endpoint `POST /user/save` with a JSON body like
    ```json
    {
      "email": "admin@a.b",
      "password": "admin",
      "roles": "USER,ADMIN"
    }
    ```
5. Click on the `My Page` link. You should see a login form.
6. Log in with the user you created.

#### Defaults
Spring Security comes with some defaults. Like how you logout and the login form.
