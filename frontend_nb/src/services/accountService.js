// services/accountService.js
import { fetchUserData } from './userService';

const API_ACCOUNTS_URL = 'http://localhost:8080/api/bank-accounts';

export async function updateAccountStatus(accountNumber, newStatus) {
    const response = await fetch(`${API_ACCOUNTS_URL}/${accountNumber}`, {
        method: 'PUT',
        body: JSON.stringify({ status: newStatus }),
        headers: { 'Content-Type': 'application/json' }
    });

    if (!response.ok) throw new Error('Error al actualizar la cuenta');

    console.log('Cuenta actualizada exitosamente');
    return fetchUserData(); // Actualizar usuario después del cambio
}
