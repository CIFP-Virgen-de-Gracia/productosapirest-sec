# productosapirest-sec
Ejemplo de como hacer un API REST en Springboot con distintos tipos de seguridad de acceso (autenticación y autorización)

El proyecto se inicia con la rama Base. A partir de aquí, este proyecto se dividirá en tres ramas, que partirán de Base, según el tipo de seguridad. Tiene como objetivo implementar una API REST en Spring Boot, con Spring Security. 
Para ello implementaremos como mecanismos de seguridad:
* Seguridad Básica HTTP
* JSON Web Token
* OAuth2

Es importante que leas como probar el API con los usuarios, contraseñas y perfiles correctos para comprobar la funcionalidad.

## Tecnologías
* H2 como base de datos embebida en modo developer (dev)
* MySQL como base de datos en modo developer (prod)
* [Lombok](https://projectlombok.org/features/all) 
* [Swagger](https://swagger.io/)  
* [Spring Security](https://spring.io/projects/spring-security)   
* [Seguridad Básica HTTP](https://es.wikipedia.org/wiki/Autenticaci%C3%B3n_de_acceso_b%C3%A1sica)   
* [JSON Web Token](https://es.wikipedia.org/wiki/JSON_Web_Token)  
* [OAuth 2](https://es.wikipedia.org/wiki/OAuth)  

### Desarrollo
* 26/03/2020: Inicio del proyecto. Basado en la unión de dos de mis reposotorios. Creación de la rama Base:
> * Gestion de usuarios para una APi REST con Spring Security: https://github.com/joseluisgs/springboot-gestusuarios
> * Desarrollo de una Api Rest avanzada: https://github.com/joseluisgs/productosapirest-avz
> * Rama: [Base](https://github.com/joseluisgs/productosapirest-sec/tree/Base) 

##### Seguridad Basica HTTP
> * Rama: [SegBasicaHTTP](https://github.com/joseluisgs/productosapirest-sec/tree/SegBasicaHTTP)   
* 27/03/2020: Partiendo de [Base](https://github.com/joseluisgs/productosapirest-sec/tree/Base). Configuración de Seguridad HTTP. EntryPoint y filtrado de peticiones. Refactorización. Testeo y Pruebas. Fin

##### Seguridad JWT
> * Rama: [SegJWT](https://github.com/joseluisgs/productosapirest-sec/tree/SegJWT)   
* 27/03/2020: Partiendo de [Base](https://github.com/joseluisgs/productosapirest-sec/tree/Base). Configuración de librerías Java JWT
* 28/03/2020: Configuración de Seguridad y punto de entrada. Refactorización Usuarios. Manejo de Tokens y JWT
* 28/03/2020: Refactorización, Testeo y Pruebas. Fin

##### Seguridad OAuth 2
> * Rama: [SegOAuth2](https://github.com/joseluisgs/productosapirest-sec/tree/SegOAuth2)   
* 29/03/2020: Partiendo de [Base](https://github.com/joseluisgs/productosapirest-sec/tree/Base). 
* 30/03/2020: Configuración de proyecto.
* 30/03/2020: Servidor de Autentificación. Servidor de Recursos. Conf. CORS
* 30/03/2020: Refactorización. Creación de [Cliente Web](https://github.com/joseluisgs/productosapirest-oauth-cliente) 
* 30/03/2020: OAuth Tokens en Base de Datos. Uso de JWT como Tokens. Testeo y Pruebas. Fin


#### Ejecución
http://localhost:8080/api/{recurso}
> http://localhost:8080/api/productos
> Ver fichero Postman o documentación Swagger

##### Pruebas
* Se incluye el proyecto en fichero de configuración de PostMan en el directorio /postman
* Se debe tener en cuenta que para probar la API debes activar la Autenticación en Postman por el método que se esté siguiendo en cada apartado o rama para accder con el usuario del perfil adecuado.
* Ejemplos:
> * Usuario rol ADMIN:
>   * user: admin
>   * pass: Admin1
> 
> * Usuario rol USER:
>   * user: marialopez
>   * pass: Marialopez1
>
> * Rutas y permisos.
>   * Registrarse todos los que se conecten
>   * Obtener podructos, todos los usuarios registrados (USER/ADMIN)
>   * Añadir, modificar o eliminar productos solo usuarios ADMIN
>   * Añadir pedidos, cualquier usuario registrado (USER/ADMIN)


##### Documentación
Documentación en Swagger:
http://localhost:8080/swagger-ui.html

##### Base de Datos
H2: http://localhost:8080/h2-console. 
> * user:sa
> * pass:
> * db: jdbc:h2:./productosapirest

MySQL: fichero de configuracion en directorio /mysql
> * user:root
> * pass: productosapirest
> * db: jdbc:h2:./productosapirest

## Acerca de
José Luis González Sánchez: [@joseluisgonsan](https://twitter.com/joseluisgonsan). Marzo de 2020.

##### Otros
Inspirado en el curso de [OpenWebinars](https://openwebinars.net/cursos/seguridad-api-rest-spring-boot/) que se recomendó al alumnado.
