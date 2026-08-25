# App registration design · ReservApp

Registro conceptual de las dos aplicaciones del tenant, basado en lo observado ejecutando el starter (`TokenController.java`, `DidacticTokenFilter.java`).

## A. Cliente — `reservapp-web`

```text
Rol: Client
Tipo de cliente: público
Client ID: reservapp-web
Redirect URI local: http://localhost:5500/index.html
Scopes solicitados: reservations.read, reservations.write
¿Utiliza client secret?: no
Flujo: Authorization Code + PKCE
```

**Justificación — cliente público:** `reservapp-web` es una SPA que corre íntegramente en el navegador (`client/index.html` + JavaScript). Cualquier valor embebido en ese código es inspeccionable por el usuario (DevTools). Por eso no tiene sentido tratar ningún valor como secreto real — de ahí que no exista `client_secret` y que la seguridad del intercambio dependa de PKCE (`code_verifier`/`code_challenge`) en lugar de una credencial estática.

## B. API — `reservapp-api`

```text
Rol: Resource Server
Nombre: reservapp-api
Audience esperada: reservapp-api
Issuer esperado: https://identity.reservapp.local
Scopes aceptados: reservations.read, reservations.write
```

`reservapp-api` nunca solicita tokens — solo los recibe (vía Gateway) y decide si confía en ellos según `iss`, `aud`, vigencia y, ya en su propia capa, `scope` y regla de negocio.

## C. Matriz de errores

| # | Caso | Componente que detecta | Respuesta | Motivo |
|---|---|---|---:|---|
| 1 | `redirect_uri` no registrada | `mock-identity` (`/authorize`) | `400 Bad Request` | El IdP compara contra la única `redirect_uri` registrada (`http://localhost:5500/index.html`); no coincide → rechaza antes de emitir código. Ver evidencia en `README.md` §12. |
| 2 | Token emitido por issuer desconocido | Gateway (`DidacticTokenFilter`) | `401 Unauthorized` | El filtro compara `iss` contra `EXPECTED_ISSUER`. Un token con otro issuer nunca pasa, sin importar si el resto del contenido es válido. |
| 3 | Token con audience de otra API | Gateway (`DidacticTokenFilter`) | `401 Unauthorized` | Verificado en el lab (Etapa F2, `audience=otra-api`) → `401 Audience incorrecta`. |
| 4 | Token expirado | Gateway (`DidacticTokenFilter`) | `401 Unauthorized` | El filtro compara `exp` (epoch) contra el reloj actual. No se pudo forzar en vivo (el simulador no adelanta el reloj), pero es la misma comprobación que falla para issuer/audience — mismo mecanismo, distinta condición. |
| 5 | Falta `reservations.write` | `reservapp-api` (`ReservationController.requireScope`) | `403 Forbidden` | Verificado (Etapa E1/G): token válido técnicamente, pero sin el scope requerido para `DELETE`. |
| 6 | Scope correcto pero reserva de otro usuario | `reservapp-api` (`ReservationController.cancel`) | `403 Forbidden` | Verificado (Etapa H1): `sub` del token no coincide con `ownerId` de la reserva, y `role` no es `operator`. |

**Nota importante:** los casos 1 (registro de la app) ocurren **antes** de que exista un Authorization Code — no deben confundirse con los 401/403 de la API, que ocurren en un momento posterior del flujo, con tokens ya emitidos.

## D. Pruebas obligatorias — evidencia

### Client ID no registrado

```bash
curl -G http://localhost:9000/authorize \
  --data-urlencode "clientId=reservapp-web-FALSO" \
  --data-urlencode "redirectUri=http://localhost:5500/index.html" \
  --data-urlencode "user=ana" \
  --data-urlencode "scope=reservations.read" \
  --data-urlencode "audience=reservapp-api" \
  --data-urlencode "codeChallenge=dummy"
```

```http
HTTP/1.1 400 Bad Request
{"timestamp":"...","status":400,"error":"Bad Request","path":"/authorize"}
```

**Momento del fallo:** en `/authorize`, antes de generar ningún `Authorization Code`. `TokenController` compara `clientId` contra la constante `CLIENT_ID = "reservapp-web"` y rechaza cualquier valor distinto.

### Redirect URI no registrada

```bash
curl -G http://localhost:9000/authorize \
  --data-urlencode "clientId=reservapp-web" \
  --data-urlencode "redirectUri=http://evil.example.com/callback" \
  --data-urlencode "user=ana" \
  --data-urlencode "scope=reservations.read" \
  --data-urlencode "audience=reservapp-api" \
  --data-urlencode "codeChallenge=dummy"
```

```http
HTTP/1.1 400 Bad Request
{"timestamp":"...","status":400,"error":"Bad Request","path":"/authorize"}
```

**Momento del fallo:** igual que el caso anterior — en `/authorize`, antes de emitir código. Esto es exactamente la protección que describe la guía: si el IdP aceptara cualquier `redirect_uri`, un atacante podría hacer que el resultado de la autenticación se entregue a un sitio bajo su control.

Ninguna de las dos pruebas llegó a convertirse artificialmente en un 401/403 de `reservapp-api` — ambas cortan el flujo en el IdP, tal como exige la consigna.
