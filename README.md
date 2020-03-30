# productosapirest-sec
Ejemplo de como hacer un API REST en Springboot con distintos tipos de seguridad de acceso (autenticación y autorización)

El proyecto se inicia con la rama Base. A partir de aquí, este proyecto se dividirá en tres ramas, que partirán de Base, según el tipo de seguridad. Tiene como objetivo implementar una API REST en Spring Boot, con Spring Security. 
Para ello implementaremos como mecanismos de seguridad:
* Seguridad Basica HTTP
* JWT: JSON Web Token
* OAuth 2: Open Authorization

Es importante que leas como probar el API con los usuarios, contraseñas y perfiles correctos para comprobar la funcionalidad.

## Tecnologías
* H2 como base de datos embebida en modo developer (dev)
* MySQL como base de datos en modo developer (prod)
* Lombok para potenciar las anotaciones: https://projectlombok.org/features/all
* Swagger como documentación de API: https://swagger.io/
* Spring Security: https://spring.io/projects/spring-security
* Seguridad Basica HTTP
* JWT: JSON Web Token
* OAuth 2: Open Authorization

### Desarrollo
* 26/03/2020: Inicio del proyecto. Basado en la unión de dos de mis reposotorios. Creación de la rama Base:
> * Gestion de usuarios para una APi REST con Spring Security: https://github.com/joseluisgs/springboot-gestusuarios
> * Desarrollo de una Api Rest avanzada: https://github.com/joseluisgs/productosapirest-avz
> * Rama: [Base](https://github.com/joseluisgs/productosapirest-sec/tree/Base) 

##### Seguridad OAuth 2
> * Rama: [SegOAuth2](hhttps://github.com/joseluisgs/productosapirest-sec/tree/SegOAuth2)   
* 29/03/2020: Partiendo de [Base](https://github.com/joseluisgs/productosapirest-sec/tree/Base). 
* 30/03/2020: Configuración de proyecto.
* 30/03/2020: Servidor de Autentificación.

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
>  * Registrarse todos los que se conecten
>  * Obtener podructos, todos los usuarios registrados (USER/ADMIN)
>  * Añadir, modificar o eliminar productos solo usuarios ADMIN
>  * Añadir pedidos, cualquier usuario registrado (USER/ADMIN)



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
