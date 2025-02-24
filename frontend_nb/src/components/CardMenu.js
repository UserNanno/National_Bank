import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/CardMenu.css';

// Objeto de imágenes
const images = {
    "gestionar_cuenta.png": require("../images/gestionar_cuenta.png"),
    "transferencias.png": require("../images/transferencias.png"),
    "pago_tasas.png": require("../images/pago_tasas.png"),
};

function CardMenu({ imagen, titulo, descripcion, ruta }) {
    return (
        <div className="card__menu">
            <img src={images[imagen]} alt={titulo} />
            <div className="card__menu-content">
                <h3>{titulo}</h3>
                <p>{descripcion}</p>
                <Link to={ruta} className="button">Ver más →</Link>
            </div>
        </div>
    );
}

export default CardMenu;
