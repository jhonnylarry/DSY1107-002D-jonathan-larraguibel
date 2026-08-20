# DevLog · Semana 02

## Objetivo
Comprender OAuth2/OIDC (actores, Access Token vs ID Token, 401 vs 403, autorización técnica vs de negocio) y completar la investigación de dos servicios reales que usen estos protocolos.

## Avance
- Repasé en detalle actores del protocolo (Resource Owner, Client, Authorization Server, Resource Server), diferencia entre Access Token e ID Token, y por qué 401 y 403 no son lo mismo.
- Investigué y documenté dos servicios reales con descripción, actores y diagrama: **CapCut → TikTok** (publicación delegada de video) y **Fintoc** (pago por transferencia bancaria, patrón Open Finance).
- Reorganicé mi repositorio personal completo según el estándar oficial de DSY1107 (`docs/devlog`, `practica/oauth-oidc`, `labs/api-gateway`, etc.), migrando el trabajo que tenía repartido en dos repos separados a este repositorio único.

## Bloqueo
El caso de Fintoc quedó con una nota pendiente: falta confirmar en su documentación oficial si describen explícitamente su flujo como "OAuth-like" para poder citarlo como evidencia, en vez de asumir la equivalencia conceptual. Todavía no ejecuté el laboratorio de identidad local (`mock-identity` + `gateway` + `reservapp-api` + `client`).

## Aprendizaje
No todo "Continuar con [proveedor]" es un caso OAuth2 completo. Si solo confirma identidad (login), el Resource Server queda delgado o casi inexistente — es un caso de identidad pura (OIDC). Recién se vuelve un caso rico con los 4 actores bien diferenciados cuando hay un recurso protegido real de por medio, como publicar contenido en TikTok o mover dinero vía Fintoc.

## Siguiente
Levantar el laboratorio de identidad local y ejecutar las etapas correspondientes: flujo Authorization Code + PKCE real, comparación Access Token vs ID Token, provocar 401/403, y completar `tenant-design.md` + `app-registration-design.md`.
