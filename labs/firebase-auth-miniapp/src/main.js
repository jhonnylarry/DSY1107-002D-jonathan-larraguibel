import "./style.css";
import { auth } from "./firebase";
import {
  createUserWithEmailAndPassword,
  signInWithEmailAndPassword,
  sendPasswordResetEmail,
  onAuthStateChanged,
  signOut,
  GoogleAuthProvider,
  signInWithPopup
} from "firebase/auth";

const app = document.querySelector("#app");

app.innerHTML = `
  <main class="container">
    <section class="card public-zone">
      <h1>DSY1107 · Firebase Auth Mini App</h1>
      <h2>Zona pública</h2>
      <p>Este contenido puede verlo cualquier visitante.</p>
    </section>

    <section id="auth-zone" class="card">
      <h2>Autenticación</h2>

      <form id="register-form">
        <h3>Crear cuenta</h3>
        <input id="register-email" type="email" placeholder="Correo" required />
        <input id="register-password" type="password" placeholder="Contraseña" required minlength="6" />
        <button type="submit">Registrarme</button>
      </form>

      <hr />

      <form id="login-form">
        <h3>Ingresar</h3>
        <input id="login-email" type="email" placeholder="Correo" required />
        <input id="login-password" type="password" placeholder="Contraseña" required />
        <button type="submit">Ingresar</button>
      </form>

      <button id="google-login-button" type="button">
        Ingresar con Google
      </button>

      <hr />

      <form id="reset-form">
        <h3>Recuperar contraseña</h3>
        <input id="reset-email" type="email" placeholder="Correo" required />
        <button type="submit">Enviar correo de recuperación</button>
      </form>
    </section>

    <section id="private-zone" class="card private-zone" hidden>
      <h2>Zona privada</h2>
      <p>Solo un usuario autenticado puede ver este contenido.</p>
      <p id="current-user"></p>
      <button id="logout-button" type="button">Cerrar sesión</button>
    </section>

    <p id="message" class="message" aria-live="polite"></p>
  </main>
`;

const authZone = document.querySelector("#auth-zone");
const privateZone = document.querySelector("#private-zone");
const currentUser = document.querySelector("#current-user");
const message = document.querySelector("#message");

function showMessage(text) {
  message.textContent = text;
}

// PARTE E · Register
const registerForm = document.querySelector("#register-form");

registerForm.addEventListener("submit", async (event) => {
  event.preventDefault();

  const email = document.querySelector("#register-email").value.trim();
  const password = document.querySelector("#register-password").value;

  try {
    await createUserWithEmailAndPassword(auth, email, password);
    registerForm.reset();
    showMessage("Usuario creado correctamente.");
  } catch (error) {
    console.error(error);
    showMessage(`No fue posible crear el usuario: ${error.code}`);
  }
});

// PARTE F · Login
const loginForm = document.querySelector("#login-form");

loginForm.addEventListener("submit", async (event) => {
  event.preventDefault();

  const email = document.querySelector("#login-email").value.trim();
  const password = document.querySelector("#login-password").value;

  try {
    await signInWithEmailAndPassword(auth, email, password);
    loginForm.reset();
    showMessage("Sesión iniciada correctamente.");
  } catch (error) {
    console.error(error);
    showMessage(`No fue posible iniciar sesión: ${error.code}`);
  }
});

// PARTE G · Estado de autenticación + Logout
onAuthStateChanged(auth, (user) => {
  if (user) {
    authZone.hidden = true;
    privateZone.hidden = false;
    currentUser.textContent = `Sesión activa: ${user.email ?? user.displayName ?? user.uid}`;
  } else {
    authZone.hidden = false;
    privateZone.hidden = true;
    currentUser.textContent = "";
  }
});

const logoutButton = document.querySelector("#logout-button");

logoutButton.addEventListener("click", async () => {
  try {
    await signOut(auth);
    showMessage("Sesión cerrada.");
  } catch (error) {
    console.error(error);
    showMessage(`No fue posible cerrar la sesión: ${error.code}`);
  }
});

// PARTE H · Password reset
const resetForm = document.querySelector("#reset-form");

resetForm.addEventListener("submit", async (event) => {
  event.preventDefault();

  const email = document.querySelector("#reset-email").value.trim();

  try {
    await sendPasswordResetEmail(auth, email);
    resetForm.reset();
    showMessage("Si la cuenta corresponde, revisa el correo para continuar con la recuperación.");
  } catch (error) {
    console.error(error);
    showMessage(`No fue posible solicitar la recuperación: ${error.code}`);
  }
});

// PARTE I · Google Sign-In
const googleLoginButton = document.querySelector("#google-login-button");
const googleProvider = new GoogleAuthProvider();

googleLoginButton.addEventListener("click", async () => {
  try {
    await signInWithPopup(auth, googleProvider);
    showMessage("Sesión iniciada con Google.");
  } catch (error) {
    console.error(error);
    showMessage(`No fue posible iniciar sesión con Google: ${error.code}`);
  }
});
