# Mutantes

Software que permite detección de mutantes por ADN.

### Instalación 

Dependencias:
- Spring Web
- JPA
- JUnit

- Proyecto desarrollado con Intellij IDEA.
- Se requiere instalación de MySql, configurar src/main/resources/application.properties


## Uso de la aplicación
La aplicación se encuentra alojada en http://mutante-env.eba-djc9575f.us-east-2.elasticbeanstalk.com/
La misma contiene dos servicios:
- /stats [GET]: devuelve un informe de los ADN procesados hasta el momento.
- /mutant [POST]: procesa una muestra de ADN.

### /stats
#### Precondiciones
Hacer uso del servicio mediante llamada GET, sin parámetros.

#### Postcondiciones
Retorna un objeto JSON con los siguientes datos:
- count_human_dna: cantidad de muestras diferentes procesadas.
- count_mutant_dna: cantidad de muestras de mutantes detectadas.
- ratio: relación entre cantidad de muestras de mutantes y cantidad de muestras totales.

### /mutant
#### Precondiciones
Hacer uso del servicio mediante llamada POST, ubicando la muestra de ADN (arreglo de strings) dentro del body.
- La muestra de ADN debe representar una matriz cuadrada.
- La muestra de ADN solo puede incluir las letras "A", "C", "G" y "T".

#### Postcondiciones
Posibles respuestas:
- 400 Bad Request: error en los datos incluidos en el body.
- 403 Forbidden: la muestra no corresponde a un mutante.
- 200 OK: la muestra corresponde a un mutante.
- 500 Internal server error: indica error de la aplicación en tiempo de ejecución.


## Autor

* **Leandro Percivati** - *Desarrollo completo* - [lpercivati](https://github.com/lpercivati)
