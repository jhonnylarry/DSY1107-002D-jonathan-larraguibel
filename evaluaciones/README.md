# evaluaciones/

Entregas evaluadas de la asignatura. Cada una contiene exactamente lo necesario para construir, configurar y revisar la entrega.

```text
evaluaciones/
├── ep1/
├── ep2/
├── ep3/
└── eft/
```

## EP1 · EcoPunto

En repo aparte, entregado como enlace de GitHub (así lo pide la rúbrica oficial):

🔗 [`dsy1107-ep1-ecopunto`](https://github.com/jhonnylarry/dsy1107-ep1-ecopunto) — Angular + MSAL, Spring Boot Resource Server, Microsoft Entra ID, AWS API Gateway + EC2. Mismo proyecto continúa en EP2.

## Actividad evaluada Semana 08 · RabbitMQ (10% EV2)

[`ev2-actividad-rabbitmq/`](ev2-actividad-rabbitmq/) — Diseño e implementación de comunicación síncrona y asíncrona sobre el caso EcoPunto: 4 microservicios Spring Boot, exchange topic `ecopunto.events`, evento `ReporteCreado` con dos consumers (notificaciones y estadísticas). Informe en el README.

## Actividad · Diseño de integración asíncrona (10% EV2)

[`ev2-diseno-integracion-asincrona/`](ev2-diseno-integracion-asincrona/) — Diseño (sin código) de la generación de tickets de mantención en EcoPunto: exchange direct `ecopunto.tareas`, cola `tickets.mantencion` con DLQ, ACK manual e idempotencia. Documento en el README y en [PDF](ev2-diseno-integracion-asincrona/diseno-integracion-asincrona.pdf).

## EP3 / EP4 · EcoPunto con RabbitMQ

Se implementa en el repo de EcoPunto (proyecto incremental).
