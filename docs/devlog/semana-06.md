# DevLog · Semana 06

## Objetivo
Llevar EcoPunto a la nube para la Evaluación Parcial 2: desplegar frontend y backend en EC2, poner un API Gateway que valide el JWT delante del backend, habilitar el registro de usuarios desde el frontend y dejar la presentación lista.

## Avance
- Desplegué frontend y backend en dos instancias EC2 del laboratorio de AWS Academy, cada uno en su contenedor Docker con reinicio automático.
- Descubrí que MSAL no funciona por HTTP en una IP pública: usa `crypto.subtle`, que el navegador solo expone en un contexto seguro. Primero usé un certificado autofirmado y después un dominio propio (`ecopunto.larraguibel.dev`, DNS en Cloudflare) con certificado real de Let's Encrypt.
- Creé el API Gateway como HTTP API (igual que en el lab `jonathan-api`), con rutas `/public` abiertas y `GET/POST/PUT/DELETE /api` protegidas por un JWT authorizer que valida firma, vigencia, issuer y audience. El Gateway rechaza con 401 los tokens ausentes o inválidos sin tocar el backend; el backend sigue validando y aplica los roles (403).
- Configuré CORS en el Gateway restringido al origen del frontend.
- Programé un DNS dinámico con la API de Cloudflare: cada instancia actualiza su registro al arrancar, así que reiniciar el laboratorio (que cambia las IPs) ya no requiere pasos manuales.
- Pasé la base de datos de H2 en memoria a PostgreSQL en un contenedor con volumen persistente, sin cambiar código (la conexión ya se leía de variables de entorno).
- Habilité el registro de autoservicio en Entra ID (flujo de usuario asociado a la app) y agregué un botón "Crear cuenta" en el frontend que abre el registro directo con `prompt=create`.
- Documenté la matriz 200/401/403 probada en producción y un script para repetirla desde la consola del navegador. Armé la presentación y el guion de la demo.

## Bloqueo
El preflight CORS fallaba con 401 una vez activado el authorizer: la ruta `ANY /api/{proxy+}` también captura el método `OPTIONS`, y el navegador manda el preflight sin token. Una ruta `OPTIONS` sin integración no lo arreglaba, porque el Gateway no la elige. Lo resolví recién cuando dejé de probar a ciegas y revisé la configuración completa del Gateway por la CLI: reemplacé el `ANY` por métodos explícitos y el Gateway pasó a responder el preflight por su cuenta (204).

## Aprendizaje
Una página HTTPS no puede llamar a un backend HTTP (contenido mixto), así que el API Gateway no es solo un requisito de la rúbrica: es lo que le da HTTPS al backend. También aprendí a separar las responsabilidades de seguridad: el Gateway responde "¿el token es auténtico?" y el backend "¿tiene permiso para esta operación?". Y que las integraciones con `{proxy}` reciben solo lo capturado por `{proxy+}`, sin el prefijo de la ruta, así que cada prefijo necesita su propia integración.

## Siguiente
Ensayar la demo en vivo siguiendo el guion y presentar la EP2.
