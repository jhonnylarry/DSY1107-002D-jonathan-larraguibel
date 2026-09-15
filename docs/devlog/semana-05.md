# DevLog · Semana 05

## Objetivo
Retomar EcoPunto en el PC Windows (venía trabajando en Mac), dejar la configuración real de Microsoft Entra ID funcionando de punta a punta, y completar el código necesario para cumplir los dos indicadores de la Evaluación Parcial 1.

## Avance
- Cloné los repos en el equipo nuevo y dejé configurados `.claude/settings.json` + `CLAUDE.md` locales (gitignorados) para permisos rutinarios en el repo, sin dejar ningún rastro de herramientas de IA en lo que se sube a GitHub.
- Creé el App Registration real en Microsoft Entra ID: la cuenta institucional de Duoc no tiene permisos de administrador sobre el directorio (da 401), así que usé el tenant personal que se crea automáticamente con la cuenta de Azure for Students. Configuré scopes `reportes.read`/`reportes.write`, App Role `ENCARGADO`, y un usuario de prueba con el rol asignado.
- Encontré y resolví dos bugs reales de configuración de Entra que no estaban documentados en ningún lado: (1) un App Registration nuevo emite tokens v1.0 para su propia API por defecto, aunque todo el flujo de login use el endpoint v2 — hay que forzar `requestedAccessTokenVersion: 2` en el manifiesto; (2) una vez en v2, el claim `aud` del token viene como el Client ID pelado, no como `api://<clientId>` — hubo que inspeccionar el token real para darme cuenta, en vez de asumir el formato.
- Verifiqué el login end-to-end real (Authorization Code + PKCE) del frontend contra el backend, con token real validado por Spring Security.
- Completé el CRUD de puntos limpios: antes solo había `GET` y `PUT`, agregué `POST` (crear) y `DELETE` (eliminar), ambos protegidos con `ROLE_ENCARGADO`.
- Encontré y arreglé un bug real del frontend: `materialesAceptados` se tipaba como `string[]` pero el backend lo devuelve como `string` separado por comas — el `.join()` sobre un string rompía la evaluación del template y cortaba el renderizado de la lista después del primer punto limpio.
- Rediseñé visualmente el frontend (tarjetas con chips de materiales en vez de texto plano) — versión simple, pendiente de refinar más adelante.
- Probé la matriz completa de seguridad creando un segundo usuario sin ningún rol asignado: público 200, sin token 401, autenticado sin rol suficiente 403 (en `POST`/`PUT`/`DELETE`), autenticado con el rol correcto 200/201/204.
- Instalé Docker Desktop y WSL2 en la máquina Windows para poder probar los contenedores localmente más adelante.

## Bloqueo
La máquina no tenía virtualización de hardware habilitada en el firmware (BIOS/UEFI), que es requisito para que Docker Desktop levante el motor vía WSL2. Windows no puede activarlo por software — queda pendiente entrar a la BIOS manualmente. Como es solo necesario para probar localmente (el despliegue real va a EC2, que no tiene este problema), no bloquea nada de la entrega.

## Aprendizaje
La diferencia real entre los dos mecanismos de autorización de Entra ID: los **scopes delegados** se rigen por consentimiento — el consentimiento de administrador otorgado una vez en el App Registration aplica automáticamente a todos los usuarios del tenant. Los **App Roles**, en cambio, se rigen por asignación explícita por usuario o grupo en Enterprise Applications, y no tienen nada que ver con el consentimiento. Por eso un usuario sin el rol asignado igual trae los scopes en su token, pero nunca el claim `roles` — son dos sistemas independientes que hay que configurar por separado.

## Siguiente
Con el código de EP1 cumpliendo ambos indicadores de la rúbrica (MSAL operativo end-to-end, backend valida JWT con la matriz 401/403/200 completa), lo que queda es EP2: habilitar virtualización en BIOS o compilar Docker directo en la instancia EC2, desplegar frontend y backend ahí, montar AWS API Gateway como proxy con CORS configurado, y preparar la demo en vivo.
