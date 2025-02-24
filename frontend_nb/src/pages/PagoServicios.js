import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/PagoServicios.css';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';
import { getLocalData } from '../utils/localStorageUtils';
import { fetchAvailableServices, makeServicePayment } from '../services/paymentService';

function PagoServicios() {
    const [tipoServicio, setTipoServicio] = useState('Cable');
    const [servicios, setServicios] = useState([]);
    const [codigoPago, setCodigoPago] = useState('');
    const [monto, setMonto] = useState('');
    const [cuentas, setCuentas] = useState([]);
    const [selectedService, setSelectedService] = useState('');
    const [selectedAccount, setSelectedAccount] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const user = getLocalData('user');
        if (user && user.bankAccounts) {
            setCuentas(user.bankAccounts.filter(account => account.status === 'ACTIVE'));
        }
        fetchAvailableServices().then(setServicios);
    }, []);

    const handleServiceChange = (event) => {
        setTipoServicio(event.target.value);
    };

    const handleSubmit = async () => {
        if (!codigoPago) {
            alert('Por favor, ingrese el código de pago');
            return;
        }
        if (!monto || isNaN(monto)) {
            alert('Por favor, ingrese un monto válido');
            return;
        }
        if (!selectedService) {
            alert('Seleccione un servicio para pagar');
            return;
        }
        if (!selectedAccount) {
            alert('Seleccione una cuenta para realizar el pago');
            return;
        }

        try {
            await makeServicePayment({
                serviceType: tipoServicio,
                companyName: selectedService,
                serviceCode: codigoPago,
                amount: parseFloat(monto),
                accountNumber: selectedAccount
            });
            alert('Pago realizado con éxito');
            navigate('/pago-servicios');
        } catch (error) {
            alert(`Error en el pago: ${error.message}`);
        }
    };

    return (
        <div>
            <Navbar />
            <div id='pago-servicios'>
                <h1>PAGO DE SERVICIOS</h1>

                <label htmlFor="tipo-servicio">Seleccione el tipo de servicio</label>
                <select id="tipo-servicio" value={tipoServicio} onChange={handleServiceChange}>
                    {['Cable', 'Educación', 'Financiero', 'Internet', 'Salud', 'Seguros', 'Servicios de Luz',
                        'Servicios de Agua', 'Servicios Municipales', 'Telefonía', 'Transporte'].map(service => (
                            <option key={service} value={service}>{service}</option>
                        ))}
                </select>

                <label htmlFor="servicio">Seleccione el servicio a pagar</label>
                <select id="servicio" value={selectedService} onChange={(e) => setSelectedService(e.target.value)}>
                    <option value="">Seleccione un servicio</option>
                    {servicios.filter(servicio => servicio.serviceType === tipoServicio).map(servicio => (
                        <option key={servicio.companyName} value={servicio.companyName}>{servicio.companyName}</option>
                    ))}
                </select>

                <label htmlFor="codigo-pago">Código de pago:</label>
                <input type="text" id='codigo-pago' value={codigoPago} onChange={(e) => setCodigoPago(e.target.value)} />

                <label htmlFor="monto">Monto a pagar en soles</label>
                <input type="text" id='monto' value={monto} onChange={(e) => setMonto(e.target.value)} />

                <label htmlFor="accountSelect">Seleccione la cuenta a cargo</label>
                <select id="accountSelect" value={selectedAccount} onChange={(e) => setSelectedAccount(e.target.value)}>
                    <option value="">Seleccione una cuenta</option>
                    {cuentas.map(account => (
                        <option key={account.accountNumber} value={account.accountNumber}>
                            {`Cuenta: ${account.accountNumber} - Saldo: ${account.balance}`}
                        </option>
                    ))}
                </select>

                <button onClick={handleSubmit}>Pagar servicio</button>
            </div>
            <Footer />
        </div>
    );
}

export default PagoServicios;
