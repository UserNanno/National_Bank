import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/Transferencias.css';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';
import { getLocalData } from '../utils/localStorageUtils';
import { makeTransfer } from '../services/transactionService';

function Transferencias() {
    const [tipo, setTipo] = useState(0); // 0: Transferencia interna, 1: Externa
    const [cuentas, setCuentas] = useState([]);
    const [cuentaOrigen, setCuentaOrigen] = useState('');
    const [cuentaDestino, setCuentaDestino] = useState('');
    const [cuentaDestinoExterna, setCuentaDestinoExterna] = useState('');
    const [montoTransferir, setMontoTransferir] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const user = getLocalData('user');
        if (user && user.bankAccounts) {
            const cuentasActivas = user.bankAccounts.filter(account => account.status === 'ACTIVE');
            setCuentas(cuentasActivas);
            setCuentaOrigen(cuentasActivas.length > 0 ? cuentasActivas[0].accountNumber : '');
        }
    }, []);

    useEffect(() => {
        if (tipo === 0) {
            setCuentaDestino('');
        } else {
            setCuentaDestinoExterna('');
        }
    }, [tipo]);

    const handleSubmit = async () => {
        if (!cuentaOrigen) {
            alert('Seleccione una cuenta de origen');
            return;
        }

        const cuentaDestinoFinal = tipo === 0 ? cuentaDestino : cuentaDestinoExterna;
        if (!cuentaDestinoFinal) {
            alert('Debe ingresar una cuenta de destino');
            return;
        }

        if (!montoTransferir || isNaN(montoTransferir) || parseFloat(montoTransferir) <= 0) {
            alert('El monto a transferir debe ser un número mayor a 0');
            return;
        }

        try {
            await makeTransfer({
                fromAccount: cuentaOrigen,
                toAccount: cuentaDestinoFinal,
                amount: parseFloat(montoTransferir)
            });
            alert('Transferencia realizada con éxito');
            navigate('/transferencias');
        } catch (error) {
            alert(`Error en la transacción: ${error.message}`);
        }
    };

    return (
        <div>
            <Navbar />
            <div id='transferencias'>
                <h1>¡Realiza transferencias de manera confiable y segura!</h1>

                <div className="contenido-transferencias">
                    <img src={require('../images/tranferencia.png')} alt="imagen transferencia" />
                    <p>Transfiere entre tus cuentas y cuentas externas</p>
                </div>

                <div className="transferencias">
                    <input type="radio" name="transferType" value="betweenAccounts" onChange={() => setTipo(0)} defaultChecked /> Entre cuentas propias
                    <input type="radio" name="transferType" value="toExternalAccounts" onChange={() => setTipo(1)} /> A cuentas externas
                </div>

                <div className="cuentaOrigen">
                    <label htmlFor="cuentaOrigen">Seleccione la cuenta de origen</label>
                    <select value={cuentaOrigen} onChange={(e) => setCuentaOrigen(e.target.value)}>
                        {cuentas.map(account => (
                            <option key={account.accountNumber} value={account.accountNumber}>
                                {`Cuenta: ${account.accountNumber} - Saldo: ${account.balance}`}
                            </option>
                        ))}
                    </select>
                </div>

                {tipo === 0 ? (
                    <div className="cuentaDestino">
                        <label htmlFor="cuentaDestino">Seleccione la cuenta de destino</label>
                        <select value={cuentaDestino} onChange={(e) => setCuentaDestino(e.target.value)}>
                            <option value="">Seleccione una cuenta</option>
                            {cuentas.filter(acc => acc.accountNumber !== cuentaOrigen).map(account => (
                                <option key={account.accountNumber} value={account.accountNumber}>
                                    {`Cuenta: ${account.accountNumber} - Saldo: ${account.balance}`}
                                </option>
                            ))}
                        </select>
                    </div>
                ) : (
                    <div className="cuentaDestinoExterna">
                        <label htmlFor="cuentaDestinoExterna">Ingrese la cuenta de destino</label>
                        <input type="text" value={cuentaDestinoExterna} onChange={(e) => setCuentaDestinoExterna(e.target.value)} />
                    </div>
                )}

                <div className="montoTransferir">
                    <label htmlFor="montoTransferir">Ingrese el monto a transferir en soles</label>
                    <input type="text" value={montoTransferir} onChange={(e) => setMontoTransferir(e.target.value)} />
                </div>

                <button className="ejecutarTransferencia" onClick={handleSubmit}>Transferir</button>
            </div>
            <Footer />
        </div>
    );
}

export default Transferencias;
