# 3. Levantamiento de requerimientos — Mini Help Desk

## 3.1 Problema

En la organización, las incidencias técnicas internas (problemas de acceso, errores de aplicaciones, fallas de equipos, solicitudes de soporte) se reportan hoy mediante mensajes informales, sin un registro centralizado. Esto provoca que se pierda información relevante y que no exista una visión clara de cuántas incidencias están pendientes, cuáles se están atendiendo y cuáles ya fueron resueltas.

El operador de soporte es quien enfrenta este problema directamente: no cuenta con una fuente única de verdad para priorizar su trabajo ni para dar seguimiento al estado de cada caso reportado.

La aplicación debe producir un registro centralizado y consultable de incidencias, que permita crear, revisar, actualizar, cerrar y filtrar incidencias, entregando además una visión rápida del estado general mediante un indicador simple.

## 3.2 Actores

### Operador de soporte

- **Qué necesita hacer:** registrar nuevas incidencias, revisar el listado completo, consultar el detalle de una incidencia puntual, editarla, actualizar su estado a medida que avanza su atención, eliminarla si corresponde, y filtrar/buscar incidencias para priorizar su trabajo.
- **Qué información utiliza:** título, descripción, categoría, prioridad, estado y fecha de creación de cada incidencia.
- **Qué resultado espera:** tener siempre una visión actualizada y confiable de todas las incidencias abiertas, en progreso y resueltas, sin depender de mensajes informales dispersos.

## 3.3 Requerimientos funcionales

```text
RF-01 — Registrar incidencia
El sistema debe permitir registrar una incidencia indicando título,
descripción, categoría y prioridad.

RF-02 — Listar incidencias
El sistema debe permitir visualizar el listado completo de incidencias
registradas.

RF-03 — Consultar detalle de incidencia
El sistema debe permitir consultar el detalle completo de una incidencia
a partir de su identificador.

RF-04 — Editar incidencia
El sistema debe permitir modificar el título, descripción, categoría
y/o prioridad de una incidencia existente.

RF-05 — Eliminar incidencia
El sistema debe permitir eliminar una incidencia existente.

RF-06 — Cambiar estado de incidencia
El sistema debe permitir cambiar el estado de una incidencia entre
ABIERTA, EN_PROGRESO y RESUELTA.

RF-07 — Filtrar incidencias por estado
El sistema debe permitir filtrar el listado de incidencias según su
estado actual.

RF-08 — Filtrar incidencias por prioridad
El sistema debe permitir filtrar el listado de incidencias según su
nivel de prioridad.

RF-09 — Buscar incidencias por texto
El sistema debe permitir buscar incidencias cuyo título o descripción
coincida con un texto ingresado por el usuario.

RF-10 — Mostrar indicador de estado
El sistema debe mostrar un resumen o contador con la cantidad de
incidencias por cada estado (ABIERTA, EN_PROGRESO, RESUELTA).
```

## 3.4 Requerimientos no funcionales

```text
RNF-01 — Ejecución reproducible
La aplicación completa debe poder iniciarse mediante Docker Compose
siguiendo las instrucciones del README, sin pasos manuales adicionales.

RNF-02 — Persistencia de datos
Los datos de las incidencias deben persistir aunque los contenedores
se detengan y se vuelvan a iniciar (mediante un volumen de Docker),
siempre que no se elimine explícitamente dicho volumen.

RNF-03 — Manejo de errores comprensible
Ante datos inválidos o recursos inexistentes, la API debe responder
con códigos HTTP y mensajes de error comprensibles para el frontend,
en vez de fallar silenciosamente.

RNF-04 — Usabilidad mínima
Un usuario debe poder crear, editar, cambiar de estado y eliminar una
incidencia sin más de tres interacciones (clics/envíos de formulario)
por acción.

RNF-05 — Mantenibilidad del backend
El backend debe organizar su código separando controladores, servicios,
acceso a datos y modelos en paquetes distintos, de forma que un cambio
en una capa no obligue a modificar las demás.
```

## 3.5 Reglas de negocio

```text
RN-01
Toda incidencia nueva debe comenzar en estado ABIERTA.

RN-02
El título y la descripción de una incidencia son obligatorios; no se
permite registrar una incidencia sin ambos campos.

RN-03
La prioridad de una incidencia debe ser una de las siguientes: BAJA,
MEDIA o ALTA. No se aceptan otros valores.

RN-04
El estado de una incidencia debe ser uno de los siguientes: ABIERTA,
EN_PROGRESO o RESUELTA. No se aceptan otros valores.

RN-05
No se debe permitir consultar, editar, cambiar de estado o eliminar
una incidencia cuyo identificador no exista; el sistema debe responder
con un error claro (404) en ese caso.
```

## 3.6 Criterios de aceptación

### RF-01 — Registrar incidencia

```gherkin
Dado que el usuario completó título, descripción, categoría y prioridad
Cuando registra una nueva incidencia
Entonces la incidencia queda almacenada
Y aparece en el listado principal
Y su estado inicial es ABIERTA
```

### RF-06 — Cambiar estado de incidencia

```gherkin
Dado que existe una incidencia en estado ABIERTA
Cuando el usuario cambia su estado a EN_PROGRESO
Entonces el nuevo estado queda guardado
Y el listado y el indicador de resumen reflejan el cambio
```

### RF-09 — Buscar incidencias por texto

```gherkin
Dado que existen incidencias registradas con distintos títulos
Cuando el usuario busca un texto contenido en el título de una de ellas
Entonces el listado muestra únicamente las incidencias que coinciden
Y no muestra las que no coinciden
```

## 3.7 Alcance y fuera de alcance

**Dentro del alcance:**

- CRUD completo de incidencias.
- Cambio de estado.
- Filtro por estado y por prioridad.
- Búsqueda por texto.
- Indicador/contador de incidencias por estado.

**Fuera de alcance:**

- Autenticación y manejo de usuarios/roles.
- Recuperación de contraseña.
- Envío de correos o notificaciones.
- Notificaciones push.
- Arquitectura de microservicios.
- Despliegue productivo en la nube.
- Historial detallado de auditoría por cambio de campo.
