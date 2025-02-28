import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import '../styles/Navbar.css';
import { removeLocalData } from '../utils/localStorageUtils';

export default function Navbar() {
    const navigate = useNavigate();

    const handleLogout = () => {
        removeLocalData('user'); // Solo elimina datos de usuario
        navigate('/'); // Redirige usando React Router
    };

    const navLinks = [
        { path: '/menu', label: 'Inicio' },
        { path: '/nosotros', label: 'Acerca de nosotros' },
        { path: '/contacto', label: 'Contáctanos' },
        { path: '/perfil', label: 'Mi perfil' },
    ];

    return (
        <nav className="navbar">
            <ul className="navbar__nav">
                {navLinks.map(({ path, label }) => (
                    <li key={path}>
                        <Link to={path}>{label}</Link>
                    </li>
                ))}
                <li>
                    <button className="navbar__salir" onClick={handleLogout}>Salir</button>
                </li>
            </ul>
        </nav>
    );
}
