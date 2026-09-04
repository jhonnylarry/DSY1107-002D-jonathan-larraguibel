# Evidencias · Lab Firebase Authentication

**Proyecto Firebase:** `dsy1107-larraguibel`
**Stack:** Vite + JavaScript vanilla + Firebase Authentication (Email/Password + Google)

## 1. Configuración de proveedores (Checkpoint C1)

Antes de habilitar Google:

```text
Email/Password    Enabled
Google            Disabled
```

Después de aprobar el Checkpoint H1:

```text
Email/Password    Enabled
Google            Enabled
```

## 2. Usuarios creados en Firebase

| Identificador | Proveedor | Fecha de creación |
|---|---|---|
| `jonathanlarraguibel@gmail.com` | Correo electrónico/contraseña ✉️ | 4 sept 2026 |
| `jo.larraguibel@duocuc.cl` | Google 🔴🟡🟢🔵 | 4 sept 2026 |

Cada proveedor generó un `UID` distinto — son dos identidades separadas dentro del mismo proyecto Firebase, aunque comparten la misma persona real.

## 3. Matriz de pruebas (Parte K)

| ID | Prueba | Resultado esperado | Resultado obtenido |
|---|---|---|---:|
| AUTH-01 | Abrir sin sesión | pública visible, privada oculta | ✅ PASS |
| AUTH-02 | Registrar nuevo usuario | cuenta creada y sesión iniciada | ✅ PASS — `createUserWithEmailAndPassword` autentica automáticamente |
| AUTH-03 | Registrar correo existente | operación rechazada | ✅ PASS — `auth/email-already-in-use` |
| AUTH-04 | Login correcto | privada visible | ✅ PASS |
| AUTH-05 | Login incorrecto | privada oculta | ✅ PASS — `auth/invalid-credential` |
| AUTH-06 | Recargar autenticado | sesión restaurada | ✅ PASS |
| AUTH-07 | Logout | privada oculta | ✅ PASS |
| AUTH-08 | Password reset | correo enviado | ✅ PASS — llegó a la carpeta de spam de Gmail |
| AUTH-09 | Usar nueva contraseña | login correcto | ✅ PASS |
| AUTH-10 | Google login | sesión autenticada | ✅ PASS |
| AUTH-11 | Logout después de Google | vuelve a estado público | ✅ PASS |

**11/11 casos aprobados.**

## 4. Captura relevante — usuarios en Firebase Console

Ver `Authentication > Users`: dos filas, una por proveedor, cada una con su propio `UID`, mismas fechas de creación y acceso (4 sept 2026).

## 5. Incidente durante la prueba (para el DevLog)

El correo de recuperación de contraseña (AUTH-08) no llegó a la bandeja principal de Gmail — cayó en **spam**. Es un comportamiento típico la primera vez que un proyecto Firebase nuevo envía correo, porque el dominio remitente (`noreply@dsy1107-larraguibel.firebaseapp.com`) todavía no tiene reputación establecida ante los filtros de Gmail. Se resolvió revisando la carpeta de spam manualmente.

También durante la configuración, GitHub Secret Scanning marcó el `apiKey` de `firebase.js` como "Google API Key" expuesta. Se verificó contra la guía oficial del lab y la documentación de Firebase: la configuración web (`apiKey`, `authDomain`, `projectId`, etc.) **no es un secreto** — es un identificador público del proyecto. La seguridad real de Firebase depende de las **Security Rules** y la lista de dominios autorizados, no de ocultar este valor. Se decidió no aplicar restricciones adicionales de la API key por ahora y dejar la configuración como está.

## 6. Preguntas de reflexión (Parte M)

**1. ¿Qué responsabilidad queda en Firebase y cuál queda en la aplicación?**
Firebase se encarga de almacenar y verificar credenciales, crear identidades, mantener el estado de sesión y enviar correos de recuperación. La aplicación solo decide **qué mostrar** según ese estado (zona pública vs. zona privada) — nunca maneja contraseñas ni valida credenciales por sí misma.

**2. ¿Por qué la aplicación no debe almacenar la contraseña del usuario?**
Porque la aplicación no tiene por qué asumir la responsabilidad de proteger ese dato sensible (cifrado, rotación, fugas). Delegarlo a un proveedor especializado reduce la superficie de riesgo: si mi código nunca toca la contraseña, no puede filtrarla por un error propio.

**3. ¿Qué diferencia existe entre registrar e iniciar sesión?**
Registrar (`createUserWithEmailAndPassword`) crea una identidad nueva en Firebase y dejó automáticamente al usuario autenticado. Iniciar sesión (`signInWithEmailAndPassword`) valida credenciales contra una identidad **que ya existe** — no crea nada nuevo.

**4. ¿Qué representa el objeto `user` recibido por `onAuthStateChanged`?**
Es la fuente de verdad sobre si existe una sesión válida en este momento. Si `user` existe, hay sesión (con datos como `email`, `uid`); si es `null`, no la hay. Toda la lógica de qué mostrar en la interfaz depende de este objeto, no de una variable propia que yo mantenga a mano.

**5. ¿Por qué ocultar una zona en JavaScript no es suficiente para proteger una API real?**
Porque ocultar un `<section>` con `hidden = true` es una decisión que ocurre enteramente en el navegador del usuario — cualquiera puede abrir las DevTools y quitar ese atributo, o llamar directamente a una API sin pasar por la interfaz. La protección real tiene que ocurrir en el servidor, validando el token en cada petición — es la misma idea que ya vimos en el laboratorio de identidad: ocultar un botón no reemplaza la autorización del backend.

**6. ¿Qué ventaja ofrece incorporar Google como proveedor federado?**
El usuario no necesita crear ni recordar una contraseña nueva específica para esta aplicación, y la app se beneficia de la seguridad ya establecida de la cuenta Google (2FA, detección de accesos sospechosos) sin tener que implementarla.

**7. ¿Por qué en este laboratorio implementamos primero Email/Password y después Google?**
Porque Email/Password es el caso más simple para validar que el patrón completo (registro, login, estado de sesión, logout) funciona de punta a punta. Agregar un segundo proveedor antes de tener eso resuelto mezclaría dos fuentes de posibles errores al mismo tiempo, dificultando saber cuál está fallando.

**8. Si mañana agregáramos GitHub, Microsoft o Apple como proveedor, ¿qué parte conceptual seguiría siendo la misma?**
El patrón completo de `onAuthStateChanged` decidiendo qué mostrar según exista o no un objeto `user` no cambia en absoluto — es agnóstico al proveedor. Lo único que cambiaría es qué función de Firebase se llama para iniciar el login (`GithubAuthProvider`, `OAuthProvider` para Microsoft/Apple, etc.); la forma en que la aplicación reacciona al resultado es idéntica.

## 7. Criterio de término

Secuencia completa ejecutada y verificada:

```text
1. abrir sin sesión                          ✅
2. registrar con email/password              ✅
3. entrar a zona privada                     ✅
4. cerrar sesión                             ✅
5. iniciar sesión nuevamente                  ✅
6. cerrar sesión                             ✅
7. solicitar recuperación de contraseña       ✅ (revisar spam)
8. cambiar contraseña desde el correo         ✅
9. iniciar sesión con la nueva contraseña     ✅
10. cerrar sesión                            ✅
11. habilitar Google                         ✅
12. iniciar sesión con Google                 ✅
13. entrar a la misma zona privada            ✅
14. cerrar sesión                            ✅
```

Explicación del flujo de delegación:

```text
la aplicación NO autentica por sí sola
                |
                v
      delega autenticación
                |
                v
       Firebase Authentication
                |
                v
       devuelve estado de identidad (onAuthStateChanged)
                |
                v
     la app adapta su comportamiento (mostrar u ocultar zonas)
```
