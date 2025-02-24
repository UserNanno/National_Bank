import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/CerrarCuenta.css';
import { getLocalData } from '../utils/localStorageUtils';
import { updateAccountStatus, getUserAccounts } from '../services/accountService';

function CerrarCuenta() {
    const [accounts, setAccounts] = useState([]);
    const [selectedAccount, setSelectedAccount] = useState('');
    const [pin, setPin] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const userAccounts = getUserAccounts(3); // Filtra cuentas activas y suspendidas
        setAccounts(userAccounts);
    }, []);

    const handleCloseAccount = async () => {
        const user = getLocalData('user');

        if (!selectedAccount) {
            setErrorMessage('Seleccione una cuenta para cerrar.');
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
            await updateAccountStatus(selectedAccount, 'CERRADA');
            alert('Cuenta cerrada de manera exitosa');
            navigate('/gestionar-cuenta');
        } catch (error) {
            setErrorMessage(error.message);
        }
    };

    return (
        <div className="cerrar-cuenta">
            <h2>Cerrar cuenta</h2>
            <form className="cerrar-form">
                <label htmlFor="accountSelect">Seleccione la cuenta a cerrar:</label>
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

                <label htmlFor="motivo">Motivo de cierre:</label>
                <select id="motivo">
                    <option value="">Indique el motivo...</option>
                    <option value="1">Perdida/hurto de tarjeta</option>
                    <option value="2">Sospecha de fraude</option>
                    <option value="3">Otros</option>
                </select>
                <textarea id="descripcion" placeholder="Describa brevemente"></textarea>

                <label htmlFor="pin">Ingrese su contraseña para confirmar:</label>
                <input
                    id="pin"
                    type="password"
                    placeholder="Ingrese contraseña"
                    value={pin}
                    onChange={(e) => setPin(e.target.value)}
                    required
                />

                {errorMessage && <p className="error-message">{errorMessage}</p>}

                <button type="button" onClick={handleCloseAccount}>
                    Continuar
                </button>
            </form>
        </div>
    );
}

export default CerrarCuenta;
