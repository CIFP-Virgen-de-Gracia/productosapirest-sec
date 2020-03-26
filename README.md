# productosapirest-sec
Ejemplo de como hacer un API REST en Springboot con distintos tipos de seguridad de acceso (autenticación y autorización)

Este proyecto se dividirá en tres ramas según el tipo de seguridad. Tiene como objetivo implementar una API REST en Spring Boot, con Spring Security. 
Para ello implementaremos como mecanismos de seguridad
* Seguridad Basica HTTP
* JWT: JSON Web Token
* OAth 2: Open Authorization

## Tecnologías
* H2 como base de datos embebida
* Lombok para potenciar las anotaciones: https://projectlombok.org/features/all
* Seguridad Basica HTTP
* JWT: JSON Web Token
* OAth 2: Open Authorization

### Desarrollo
* 26/03/2020: Inicio del proyecto. Basado en la unión de dos de mis reposotorios:
> * Gestion de usuarios para una APi REST con Spring Security: https://github.com/joseluisgs/springboot-gestusuarios
> * Desarrollo de una Api Rest avanzada: https://github.com/joseluisgs/productosapirest-avz
> * Rama: Base


##### Ejecución
http://localhost:8080/api/{recurso}
> http://localhost:8080/api/productos

H2: http://localhost:8080/h2-console. 
> * user:sa
> * db: jdbc:h2:./productosapirest

## Acerca de
José Luis González Sánchez: [@joseluisgonsan](https://twitter.com/joseluisgonsan)

##### Otros
Inspirado en el curso de [OpenWebinars](https://openwebinars.net/cursos/seguridad-api-rest-spring-boot/) que se recomendó al alumnado.