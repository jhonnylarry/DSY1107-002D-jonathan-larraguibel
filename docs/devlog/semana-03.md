# DevLog · Semana 03

## Objetivo
Cerrar el trabajo pendiente de Semana 02: terminar la teoría de IDaaS/CIAM y ejecutar completo el laboratorio de identidad local (`mock-identity` + `gateway` + `reservapp-api` + `client`).

## Avance
- Repasé IAM vs IdP vs IDaaS vs CIAM, y la distinción entre usuario de identidad y entidad de negocio.
- Levanté los 4 componentes del laboratorio de identidad y ejecuté todas las etapas (A–L): flujo Authorization Code + PKCE real (con traza y tokens decodificados), Access Token vs ID Token, scopes de mínimo privilegio, 401 por token faltante/audience incorrecta/ID token mal usado, 403 por scope insuficiente, 403 por regla de negocio (`sub == ownerId`), rol `operator` vs `customer`, y rechazo de `client_id`/`redirect_uri` inválidos.
- Toda la matriz de pruebas de la guía coincidió exactamente con lo esperado (10/10 casos).
- Completé `tenant-design.md` y `app-registration-design.md` con las preguntas de razonamiento de la guía.

## Bloqueo
El panel del navegador se puso inestable a mitad de la Etapa C/D (pantalla en negro, `scroll` colgado). Alcancé a capturar la traza PKCE y los tokens decodificados antes de que fallara, pero tuve que pivotar el resto de las etapas (D en adelante) a `curl`, replicando manualmente el mismo flujo PKCE (`code_verifier`/`code_challenge` vía `openssl`). También descubrí que llamar a `reservapp-api` directo (sin pasar por el Gateway) sin token da `400`, no `401` — el `401` bien formado es una decisión explícita del Gateway, no un comportamiento automático de Spring.

## Aprendizaje
El Gateway centraliza toda la validación técnica (tipo de token, issuer, audience, expiración) antes de que la petición llegue al backend — eso es lo que hace que las Etapas F fallen todas *antes* de `reservapp-api`. En cambio, la autorización de negocio (scope suficiente, `sub == ownerId`) solo puede vivir en el backend, porque es el único componente con acceso a los datos reales de las reservas. Confirmé además que "role" y "scope" son ejes independientes: el operador necesita *ambos* — el scope técnico y el rol — para poder cancelar reservas ajenas.

## Siguiente
Revisar si falta la Clase 3 (Tenant) y Clase 4 (App registration) como teoría formal separada, o si el trabajo del laboratorio ya las cubre suficientemente. Continuar con los temas de las próximas semanas.
