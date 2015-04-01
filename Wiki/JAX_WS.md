

JAX-WS Five Minute Tutorial

http://java.dzone.com/articles/jax-ws-hello-world

## HTTP basic authentication ##

### Client part ###

http://etfdevlab.blogspot.com/2009/12/http-basic-authentication-with-jax-ws.html

#### Problem : can not access the WSDL ####

The following error message occurs on `Service service = Service.create(url, qname);`

```bash
Server returned HTTP response code: 401 for URL: http://localhost:8080/w...
```

**Fix:**

Inserting the following code before the `Service service = Service.create(url, qname);`.

```java
import java.net.*;
...
Authenticator.setDefault(new Authenticator() {
 @Override
 protected PasswordAuthentication getPasswordAuthentication() {
   return new PasswordAuthentication(
     USER_NAME,
     PASSWORD.toCharArray());
 }
});
```