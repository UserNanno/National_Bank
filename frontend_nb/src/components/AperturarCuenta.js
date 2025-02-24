import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/AperturarCuenta.css';
import { getLocalData } from '../utils/localStorageUtils';
import { createBankAccount } from '../services/accountService';

function AperturarCuenta() {
    const [pin, setPin] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();

    const handleClick = async () => {
        const user = getLocalData('user');

        if (!user) {
            setErrorMessage('No se encontró información del usuario.');
            return;
        }

        if (!pin) {
            setErrorMessage('Ingrese su contraseña.');
            return;
        }

        if (pin !== user.password) {
            setErrorMessage('Contraseña incorrecta.');
            return;
        }

        try {
            await createBankAccount(user.id);
            navigate('/gestionar-cuenta'); // Redirigir a la página correcta
        } catch (error) {
            setErrorMessage(error.message);
        }
    };

    return (
        <div className="crear-cuenta-form">
            <h2>Nueva Cuenta</h2>
            <img src={require('../images/tarjeta01.png')} alt="Tarjeta bancaria" />
            <form>
                <label htmlFor="pin">Para aperturar una cuenta, ingrese su contraseña</label>
                <input
                    id="pin"
                    type="password"
                    placeholder="Ingrese contraseña"
                    value={pin}
                    onChange={(e) => setPin(e.target.value)}
                    required
                />
            </form>
            {errorMessage && <p className="error-message">{errorMessage}</p>}
            <button onClick={handleClick}>Continuar</button>
        </div>
    );
}

export default AperturarCuenta;
