# Lab · Mini App con Firebase Authentication

**Asignatura:** DSY1107 · Desarrollo Cloud Native I · Sección 002D
**Semana:** 4 · Identity as a Service
**Guía original:** [`labs/firebase-auth-miniapp/README.md`](https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/blob/master/labs/firebase-auth-miniapp/README.md) del repositorio del curso

Mini app Vite + JavaScript vanilla que delega autenticación a **Firebase Authentication**, primero con Email/Password y después con Google Sign-In.

## Cómo ejecutar

```bash
cd labs/firebase-auth-miniapp
npm install
npm run dev
```

Abre `http://localhost:5173`.

> El proyecto Firebase (`dsy1107-larraguibel`) ya está configurado en `src/firebase.js`. El `apiKey` y demás valores no son secretos — son la configuración pública que identifica el proyecto (ver `docs/evidencias.md` sección 5 para la aclaración completa sobre esto).

## Qué construye

```text
zona pública → Register → Login Email/Password → Password Reset → zona privada → Logout
                                                                        ↓
                                                              Login con Google
```

## Evidencia completa

→ [`docs/evidencias.md`](docs/evidencias.md) — matriz de pruebas (11/11 casos), usuarios creados, respuestas de reflexión, e incidentes reales encontrados durante la ejecución (correo de recuperación en spam, alerta de GitHub sobre la API key).
