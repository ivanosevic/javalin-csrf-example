
# Javalin CSRF Example

## About the project
This project was inspired by the lack of CSRF implementations that the Javalin framework has. Nonetheless, it's a simple and fast to implement
basic web features; CSRF is not an exception. The current implementation takes advice from the multiple sources listed in the section [To learn more](#to-learn-more)

The example project is an app to store notes. Each note has a title and a message. You can add notes as well by click the add button. The most important highlight is that the form is protected for CSRF attacks.

After compiling and running the project, the application can be accessed by the browser using the following link:
```
http://localhost:8080
```

## Implementation

The CSRF token is stored in the server using sessions. Each token is randomly generated using the RandomSecure class, because it
provides a cryptographically strong random number generator and the output is non-deterministic, which is ideal for this purpose. 

### CsrfFilter

#### About
To instantiate a CsrfFilter, it's necessary to create a CsrfRepository. It can be easily accomplish by doing the following:

```java
var csrfRepository = new CsrfRepository();
var csrfFilter = new CsrfFilter(csrfRepository);
```

With the newly created CsrfFilter, we can pass in the two following functions for our use case:
- We want to protect the page with the form: Use generateToken
- We want to verify the CSRF token: Use validateToken


#### Usage

To apply such filter, we need to identify which routes we want to protect. This project has the following routes:

```java
app.get("/notes", ctx -> ...);
app.post("/notes/new", ctx -> ...);
```

In this case, we want notes to receive a newly created token each time a user request the page, this is because
this page has the form we want to protect. Also, we have the POST, which we want to verify the CSRF token. To do accomplish such task,
we can do the following:

```java
app.before("/notes", csrfFilter::generateToken);
app.before("/notes/new", csrfFilter::validateToken);
```

We go back to our controller, and ask the CsrfRepository the valid token for the user, and pass it
as a model for our template engine. This is the GET handler for showing the notes:
```java 
    public void showNotesPage(Context ctx) {
        var notes = noteRepository.findAll();
        var csrfToken = csrfRepository.get(ctx);
        Boolean wasNoteCreated = ctx.consumeSessionAttribute("noteCreated");
        var viewData = new HashMap<String, Object>();
        viewData.put("csrfToken", csrfToken);
        viewData.put("notes", notes);
        viewData.put("wasNoteCreated", wasNoteCreated);
        ctx.render("templates/notes.ftl", viewData);
    }
``` 

Inside our template page, we pass in the csrf as a hidden input:
```html 
<input type="hidden" name="_csrf" value="${csrfToken}"/>
```

In case that the token is invalid, a new exception will be thrown in the before method: InvalidCsrfTokenException. 
You can customize the output for your use case, by simply catching the exception using the Javalin's convention:

``` java
    public void handleInvalidCsrfTokenException(InvalidCsrfTokenException ex, Context ctx) {
        // Handle the result in the way you want...
    }
    
    // Then, we register the exception in our Javalin instance
    app.exception(InvalidCsrfTokenException.class, csrfFilter::handleInvalidCsrfTokenException);
```

## To learn more

Here are some articles / videos to learn more about CSRF attacks and preventions:

- [CSRF Attacks](https://owasp.org/www-community/attacks/csrf)
- [CSRF Explained By PwnFunction](https://youtu.be/eWEgUcHPle0)
- [OWAS CSRF Cheat Sheet Synchronzer Pattern](https://cheatsheetseries.owasp.org/cheatsheets/Cross-Site_Request_Forgery_Prevention_Cheat_Sheet.html#synchronizer-token-pattern)

## For the future:
- Make multiple strategies for reading the CSRF Token. This project only supports reading it through form parameters, but it's easy to adapt it for other ways such as headers and cookies. 
- Implement multiple strategies for the current repository. The current repository only uses the server side session.
- Make an easy-to-install plugin for Javalin, to start using CSRF. 