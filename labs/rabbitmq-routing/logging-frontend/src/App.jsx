import React from 'react';
import './App.css';

function App() {

  const sendLog = async (level, message) => {
    const backendUrl = 'http://localhost:8080/log';
    try {
      const response = await fetch(backendUrl, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ level, message }),
      });
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const result = await response.text();
      console.log(result);
      alert(`Log '${level}' enviado!`);
    } catch (error) {
      console.error("Error al enviar el log:", error);
      alert("Error al enviar el log. Revisa la consola.");
    }
  };

  return (
    <div className="App">
      <h1>Sistema de Logging RabbitMQ</h1>
      <div className="button-container">
        <button className="info" onClick={() => sendLog('INFO', 'El usuario ha iniciado sesión.')}>
          Enviar Log INFO
        </button>
        <button className="warning" onClick={() => sendLog('WARNING', 'El uso de CPU está al 85%.')}>
          Enviar Log WARNING
        </button>
        <button className="error" onClick={() => sendLog('ERROR', 'No se pudo conectar a la base de datos.')}>
          Enviar Log ERROR
        </button>
      </div>
    </div>
  );
}

export default App;
