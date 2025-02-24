import React, { useState, useEffect } from 'react';
import '../styles/Perfil.css';
import DatosPersonales from '../components/DatosPersonales';
import EstadoCuenta from '../components/EstadoCuenta';
import { getLocalData } from '../utils/localStorageUtils';

// Importación directa de imágenes
import userPhoto from '../images/foto-usuario.png';
import logoBlanco from '../images/logo-blanco.png';

function Perfil() {
    const [user, setUser] = useState({ firstName: 'Nombre', lastName: 'Apellido' });
    const [activeComponent, setActiveComponent] = useState(1);

    useEffect(() => {
        const userData = getLocalData('user');
        if (userData) {
            setUser({ firstName: userData.firstName, lastName: userData.lastName });
        }
    }, []);

    return (
        <div>

            <div id='perfil'>
                <section className='banner-lateral'>
                    <img className="userPhoto" alt="Foto de usuario" src={userPhoto} />
                    <h3><span>{user.firstName}</span></h3>
                    <h3><span>{user.lastName}</span></h3>
                    <hr />
                    <nav>
                        <ul>
                            <li><button onClick={() => setActiveComponent(1)}>Mi Perfil</button></li>
                            <li><button onClick={() => setActiveComponent(2)}>Estado de Cuentas</button></li>
                        </ul>
                    </nav>
                    <img className="logo" alt="Logo de la empresa" src={logoBlanco} />
                </section>

                <div>
                    <main>
                        {activeComponent === 1 ? <DatosPersonales /> : <EstadoCuenta />}
                    </main>
                </div>
            </div>

        </div>
    );
}

export default Perfil;
