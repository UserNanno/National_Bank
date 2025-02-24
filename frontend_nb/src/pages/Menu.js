import React from 'react';
import Banner from '../components/Banner';
import CardMenu from '../components/CardMenu';
import '../styles/Menu.css';

// Importación de imágenes para evitar `require()`
import gestionarCuentaImg from '../images/gestionar_cuenta.png';
import transferenciasImg from '../images/transferencias.png';
import pagoTasasImg from '../images/pago_tasas.png';

export default function Menu() {
    const menuOptions = [
        {
            imagen: gestionarCuentaImg,
            titulo: 'Gestionar cuenta',
            descripcion: 'Tener una cuenta nunca fue tan fácil. Apertura de cuenta y familiarízate de todo...',
            ruta: '/gestionar-cuenta'
        },
        {
            imagen: transferenciasImg,
            titulo: 'Realizar transferencias',
            descripcion: 'Realiza transferencias y aprovecha todas las opciones para gestionar tus finanzas de forma rápida ...',
            ruta: '/transferencias'
        },
        {
            imagen: pagoTasasImg,
            titulo: 'Pagar servicios',
            descripcion: 'Realiza el pago de tus servicios de forma rápida y sencilla ...',
            ruta: '/pago-servicios'
        }
    ];

    return (
        <div>
            <Banner />
            <div className='cards__menu'>
                {menuOptions.map((option, index) => (
                    <CardMenu
                        key={index}
                        imagen={option.imagen}
                        titulo={option.titulo}
                        descripcion={option.descripcion}
                        ruta={option.ruta}
                    />
                ))}
            </div>
        </div>
    );
}
