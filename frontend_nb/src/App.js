import React from "react";
import { Routes, Route } from "react-router-dom";  // ✅ Ya no importamos BrowserRouter

// Importamos componentes generales
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";

// Importamos las páginas
import Contacto from "./pages/Contacto";
import GestionarCuenta from "./pages/GestionarCuenta";
import Inicio from "./pages/Inicio";
import Menu from "./pages/Menu";
import Nosotros from "./pages/Nosotros";
import PagoServicios from "./pages/PagoServicios";
import Perfil from "./pages/Perfil";
import Registro from "./pages/Registro";
import Transferencias from "./pages/Transferencias";

// Importamos los estilos globales
import "./styles/global.css";
import "./styles/responsive.css";



function App() {
  return (
    <>
      <Navbar />
      <Routes>
        <Route path="/" element={<Inicio />} />
        <Route path="/nosotros" element={<Nosotros />} />
        <Route path="/contacto" element={<Contacto />} />
        <Route path="/transferencias" element={<Transferencias />} />
        <Route path="/pago-servicios" element={<PagoServicios />} />
        <Route path="/menu" element={<Menu />} />
        <Route path="/registro" element={<Registro />} />
        <Route path="/gestionar-cuenta" element={<GestionarCuenta />} />
        <Route path="/perfil" element={<Perfil />} />
      </Routes>
      <Footer />
    </>
  );
}

export default App;
