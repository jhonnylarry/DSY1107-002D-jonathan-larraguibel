# DevLog · Semana 04

## Objetivo
Completar el laboratorio guiado de Firebase Authentication (IDaaS real) que forma parte del cierre de Identity as a Service de Semana 04, y revisar en detalle los requerimientos de la Evaluación Parcial 1 para empezar a planificar el proyecto nuevo que exige.

## Avance
- Construí completa la mini app Vite + JS vanilla con Firebase Authentication: Register, Login, Password Reset, `onAuthStateChanged`, Logout, y Google Sign-In como segundo proveedor — siguiendo el gate obligatorio de la guía (Email/Password 100% validado antes de tocar Google).
- Ejecuté y aprobé los 11 casos de la matriz de pruebas (AUTH-01 a AUTH-11).
- Confirmé en Firebase Console dos usuarios reales con proveedores distintos (correo/contraseña y Google), cada uno con su propio UID.
- Revisé a fondo los 7 documentos de requisitos de la Evaluación Parcial 1 (arquitectura, frontend, backend, seguridad/identidad, API Manager, demostrabilidad, plan de código base) y aclaré con evidencia del propio repo del curso que RegistrApp y el proyecto de EV1 son cosas separadas — no hay ningún cruce documentado entre ambos.
- Definí que el proyecto de EV1 usará EC2 (frontend y backend en instancias separadas, BD local en la del backend), API Gateway de AWS, y Azure Entra ID para identidad.

## Bloqueo
El correo de recuperación de contraseña de Firebase (AUTH-08) no llegó a la bandeja principal — cayó en spam, típico de un proyecto nuevo sin reputación de envío todavía. Además, GitHub marcó el `apiKey` de `firebase.js` como secreto expuesto; confirmé contra la documentación oficial de Firebase que ese valor no es un secreto real (a diferencia de un service account key), así que decidí dejarlo tal cual sin aplicar restricciones adicionales por ahora.

## Aprendizaje
Firebase resuelve el patrón de IDaaS de forma mucho más simple que Entra+MSAL: un solo `onAuthStateChanged` decide toda la UI según exista o no un `user`, sin manejar `code_verifier`/`code_challenge` a mano. La contrapartida es que Firebase no separa limpiamente ID Token de Access Token como sí lo hace OIDC completo — el mismo token cumple ambos roles. También aprendí que un "secreto detectado" por escaneo automático de GitHub no siempre es un secreto real: hay que verificar contra la documentación del proveedor antes de asumir que hay que ocultarlo.

## Siguiente
Elegir el dominio definitivo del proyecto nuevo para EV1 (candidatos: puntos limpios de reciclaje, objetos perdidos del campus, o mentoría entre estudiantes) y empezar a levantar la arquitectura EC2 + API Gateway + Entra ID.
