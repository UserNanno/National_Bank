import { getLocalData } from '../utils/localStorageUtils';

const API_BASE_URL = 'http://localhost:8080/api/service-payments';

export async function fetchAvailableServices() {
    const response = await fetch(`${API_BASE_URL}/available-services`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' }
    });

    if (!response.ok) {
        throw new Error('Error al obtener los servicios disponibles');
    }

    return response.json();
}

export async function makeServicePayment(paymentData) {
    const user = getLocalData('user');
    if (!user) throw new Error('Usuario no autenticado');

    const response = await fetch(`${API_BASE_URL}/pay/${user.id}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            serviceType: paymentData.serviceType,
            companyName: paymentData.companyName,
            serviceCode: paymentData.serviceCode,
            amount: paymentData.amount,
            bankAccount: {
                accountNumber: paymentData.accountNumber
            }
        })
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || 'Error en el pago');
    }

    return response.json();
}
