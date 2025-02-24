import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/SuspenderCuenta.css';
import { getLocalData } from '../utils/localStorageUtils';
import { updateAccountStatus, getUserAccounts } from '../services/accountService';

function SuspenderCuenta() {
    const [accounts, setAccounts] = useState([]);
    const [selectedAccount, setSelectedAccount] = useState('');
    const [pin, setPin] = useState('');
    const [motivo, setMotivo] = useState('');
    const [descripcion, setDescripcion] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        setAccounts(getUserAccounts(1)); // Filtra cuentas activas
    }, []);

    const handleSuspendAccount = async () => {
        const user = getLocalData('user');

        if (!selectedAccount) {
            setErrorMessage('Seleccione una cuenta para suspender.');
            return;
        }

        if (!motivo) {
            setErrorMessage('Seleccione un motivo.');
            return;
        }

        if (!descripcion) {
            setErrorMessage('Describa brevemente el motivo de la suspensión.');
            return;
        }

        if (!pin) {
            setErrorMessage('Debe ingresar una contraseña.');
            return;
        }

        if (pin !== user.password) {
            setErrorMessage('Contraseña incorrecta.');
            return;
        }

        try {
            await updateAccountStatus(selectedAccount, 'SUSPENDIDA');
            alert('Cuenta suspendida de manera exitosa');
            navigate('/gestionar-cuenta');
        } catch (error) {
            setErrorMessage(error.message);
        }
    };

    return (
        <div id="suspender-cuenta">
            <h2>Suspender cuenta</h2>
            <form className="suspender-form">
                <label htmlFor="accountSelect">Seleccione una cuenta</label>
                <select
                    id="accountSelect"
                    value={selectedAccount}
                    onChange={(e) => setSelectedAccount(e.target.value)}
                >
                    <option value="">Seleccione una cuenta...</option>
                    {accounts.map(account => (
                        <option key={account.id} value={account.id}>
                            {`Cuenta: ${account.accountNumber} - Saldo: ${account.balance} - Estado: ${account.status}`}
                        </option>
                    ))}
                </select>

                <label htmlFor="motivo">Motivo de suspensión</label>
                <select id="motivo" value={motivo} onChange={(e) => setMotivo(e.target.value)}>
                    <option value="">Indique el motivo...</option>
                    <option value="1">Perdida/hurto de tarjeta</option>
                    <option value="2">Sospecha de fraude</option>
                    <option value="3">Otros</option>
                </select>

                <label htmlFor="descripcion">Descripción</label>
                <textarea
                    id="descripcion"
                    placeholder="Describa brevemente"
                    value={descripcion}
                    onChange={(e) => setDescripcion(e.target.value)}
                ></textarea>

                <label htmlFor="pin">Para suspender una cuenta, ingrese su contraseña</label>
                <input
                    id="pin"
                    type="password"
                    placeholder="Ingrese contraseña"
                    value={pin}
                    onChange={(e) => setPin(e.target.value)}
                    required
                />

                {errorMessage && <p className="error-message">{errorMessage}</p>}

                <button type="button" onClick={handleSuspendAccount}>
                    Continuar
                </button>
            </form>
        </div>
    );
}

export default SuspenderCuenta;
