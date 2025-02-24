import React, { useState } from 'react';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';
import AperturarCuenta from '../components/AperturarCuenta';
import SuspenderCuenta from '../components/SuspenderCuenta';
import ReactivarCuenta from '../components/ReactivarCuenta';
import CerrarCuenta from '../components/CerrarCuenta';
import '../styles/GestionarCuenta.css';

function GestionarCuenta() {
    const [activeComponent, setActiveComponent] = useState(null);

    const renderActiveComponent = () => {
        switch (activeComponent) {
            case 'Aperturar':
                return <AperturarCuenta />;
            case 'Suspender':
                return <SuspenderCuenta />;
            case 'Reactivar':
                return <ReactivarCuenta />;
            case 'Cerrar':
                return <CerrarCuenta />;
            default:
                return null;
        }
    };

    return (
        <div>
            <Navbar />
            <main className='gestionar-cuenta'>
                <h1>¡Realiza trámites de manera confiable y segura!</h1>
                <p>Tenemos las siguientes opciones para ti</p>
                <div className="gestionar-cuenta__contenido">
                    <section className="gestionar-cuenta__contenido__menu">
                        <h3 className="gestionar-cuenta__title">Cuenta</h3>
                        <ul className="gestionar-cuenta__list">
                            <li><button onClick={() => setActiveComponent('Aperturar')} aria-label="Aperturar nueva cuenta">Aperturar nueva cuenta</button></li>
                            <li><button onClick={() => setActiveComponent('Suspender')} aria-label="Suspender cuenta temporalmente">Suspender cuenta temporalmente</button></li>
                            <li><button onClick={() => setActiveComponent('Reactivar')} aria-label="Reactivar cuenta">Reactivar cuenta</button></li>
                            <li><button onClick={() => setActiveComponent('Cerrar')} aria-label="Cerrar cuenta">Cerrar cuenta</button></li>
                        </ul>
                    </section>
                    <section className="gestionar-cuenta__info">
                        {renderActiveComponent()}
                    </section>
                </div>
            </main>
            <Footer />
        </div>
    );
}

export default GestionarCuenta;
