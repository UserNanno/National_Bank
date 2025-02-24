import React, { useEffect, useState } from 'react';
import '../styles/EstadoCuenta.css';
import { getLocalData } from '../utils/localStorageUtils';

function EstadoCuenta() {
    const [accounts, setAccounts] = useState([]);

    useEffect(() => {
        const user = getLocalData('user');
        if (user && user.bankAccounts) {
            setAccounts(user.bankAccounts);
        }
    }, []);

    return (
        <div id="cuentas">
            <h2>Tus cuentas</h2>
            <div className="cards-cuentas">
                {accounts.length > 0 ? (
                    accounts.map(account => (
                        <div key={account.id} className="card-cuenta">
                            <h3>Cuenta: {account.accountNumber}</h3>
                            <p>Saldo: {account.balance}</p>
                            <p>Estado: {account.status}</p>
                        </div>
                    ))
                ) : (
                    <p>No tienes cuentas registradas.</p>
                )}
            </div>
        </div>
    );
}

export default EstadoCuenta;
