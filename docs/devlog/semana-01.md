# DevLog · Semana 01

## Objetivo
Comprender API Gateway (routing, versionado, filtros, CORS) mediante Spring Cloud Gateway antes de usar Amazon API Gateway, y avanzar en el diagnóstico fullstack.

## Avance
- Completé el Laboratorio 1 (Partes A–F): backend directo vs. vía gateway, CRUD completo (GET/POST/PUT/DELETE), ruta `/api/v2` con versionado, header transversal `X-Gateway-Lab`, política CORS con `globalcors`.
- Encontré y resolví un bug real: el gateway y JSONPlaceholder mandaban cada uno su propio `Access-Control-Allow-Origin`, y el navegador rechazaba la respuesta por header duplicado. Se solucionó con `DedupeResponseHeader`.
- Documenté todo con evidencia reproducible (`curl` + navegador real, incluyendo prueba de origen no permitido) en `docs/evidencias.md` y el `README.md` del lab.

## Bloqueo
No tenía Maven instalado; tuve que instalarlo antes de poder ejecutar nada. También me costó entender por qué la prueba "antes de configurar CORS" no mostraba ningún fallo con el cliente provisto — era porque solo hace un GET simple sin preflight, y JSONPlaceholder ya es permisivo por su cuenta. Tuve que probar el preflight directamente con `curl` para ver el fallo real.

## Aprendizaje
CORS se resuelve sobre el header final que recibe el navegador, sin importar quién lo puso. Un backend permisivo puede "filtrarse" a través de un gateway sin política propia, y agregar CORS sin considerar lo que ya envía el backend puede terminar duplicando headers.

## Siguiente
Iniciar el diagnóstico Mini Help Desk (fullstack Java+React+Docker), todavía pendiente.
