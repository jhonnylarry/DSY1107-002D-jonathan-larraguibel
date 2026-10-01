# labs/

Laboratorios de mayor alcance. Cada uno vive en su propia carpeta con todo lo necesario para reproducirlo.

## Laboratorios

- [`api-gateway/`](api-gateway/) — Laboratorio 1 · API Gateway local con Spring Cloud Gateway (routing, versionado v1/v2, header transversal, CORS). Completo.
- [`identidad-local/`](identidad-local/) — Laboratorio · ReservApp identidad, autorización e IDaaS (Semana 02): `mock-identity` + `gateway` + `reservapp-api` + `client`, flujo Authorization Code + PKCE, 401/403, tenant y app registration design. Completo.
- [`AWS/`](AWS/) — Actividades 1.1.2 y 1.1.4 en Amazon API Gateway real (AWS Academy): HTTP API `jonathan-api`, integración con `mindicador.cl`, CORS. Contraparte cloud de `api-gateway/`.
- [`firebase-auth-miniapp/`](firebase-auth-miniapp/) — Laboratorio guiado de IDaaS real (Semana 04): mini app Vite + JS vanilla con Firebase Authentication (Email/Password + Google), 11/11 casos de la matriz de pruebas. Completo.
- [`rabbitmq/`](rabbitmq/) — Laboratorio 2.1.2 · Hello World con RabbitMQ en Docker: cola `hello`, productor (`Sender`) y consumidor (`Receiver`) con Spring AMQP, menú por consola y endpoint REST. Probado end-to-end.
- [`rabbitmq-routing/`](rabbitmq-routing/) — Laboratorio 2.1.3 · Exchanges, bindings y routing keys: sistema de logging con `DirectExchange`, colas `all_logs_queue` y `errors_only_queue`, backend Spring Boot y frontend Vite + React. Probado end-to-end.

## Del laboratorio conceptual al laboratorio cloud

Se completará cuando exista el par de laboratorios (local/neutral → cloud real) para cada tema, según pide el estándar del curso.
