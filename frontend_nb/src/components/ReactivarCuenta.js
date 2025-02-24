import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/ReactivarCuenta.css';
import { getLocalData } from '../utils/localStorageUtils';
import { updateAccountStatus, getUserAccounts } from '../services/accountService';

function ReactivarCuenta() {
    const [accounts, setAccounts] = useState([]);
    const [selectedAccount, setSelectedAccount] = useState('');
    const [pin, setPin] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        setAccounts(getUserAccounts(2)); // Filtra cuentas suspendidas
    }, []);

    const handleReactivateAccount = async () => {
        const user = getLocalData('user');

        if (!selectedAccount) {
            setErrorMessage('Seleccione una cuenta para reactivar.');
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
            await updateAccountStatus(selectedAccount, 'ACTIVE');
            alert('Cuenta reactivada de manera exitosa');
            navigate('/gestionar-cuenta');
        } catch (error) {
            setErrorMessage(error.message);
        }
    };

    return (
        <div id="reactivar-cuenta">
            <h2>Reactivación de cuenta</h2>
            <form className="reactivationForm">
                <label htmlFor="accountSelect">Seleccionar cuenta</label>
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

                <label htmlFor="pin">Clave web</label>
                <input
                    id="pin"
                    type="password"
                    placeholder="Ingrese su clave web"
                    value={pin}
                    onChange={(e) => setPin(e.target.value)}
                />

                {errorMessage && <p className="error-message">{errorMessage}</p>}

                <button type="button" onClick={handleReactivateAccount}>
                    Reactivar
                </button>
            </form>
        </div>
    );
}

export default ReactivarCuenta;
