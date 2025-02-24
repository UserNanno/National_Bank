import React from 'react';
import '../styles/Banner.css';
import { getLocalData } from '../utils/localStorageUtils';

function Banner() {
    // Inicializa con el nombre del usuario directamente
    const user = getLocalData('user');
    const [userName] = user?.firstName || 'Usuario';

    return (
        <div className="banner">
            <h1>¡Bienvenido, <span>{userName}</span>!</h1>
            <p>Navega seguro en tu banca por internet</p>
        </div>
    );
}

export default Banner;
