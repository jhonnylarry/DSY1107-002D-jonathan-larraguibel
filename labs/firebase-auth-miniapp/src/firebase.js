import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";

const firebaseConfig = {
  apiKey: "AIzaSyAzEwRsZBSjM1RwwzBS1Vh1gwnIoq_2JAU",
  authDomain: "dsy1107-larraguibel.firebaseapp.com",
  projectId: "dsy1107-larraguibel",
  storageBucket: "dsy1107-larraguibel.firebasestorage.app",
  messagingSenderId: "98222285912",
  appId: "1:98222285912:web:e133f4a333f984c417962d"
};

const app = initializeApp(firebaseConfig);

export const auth = getAuth(app);
