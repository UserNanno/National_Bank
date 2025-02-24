// services/accountService.js
import { fetchUserData } from './userService';
import { getLocalData } from '../utils/localStorageUtils';

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

export async function createBankAccount(userId) {
    const response = await fetch(`${API_ACCOUNTS_URL}/create/${userId}`, {
        method: 'POST',
        body: JSON.stringify({}),
        headers: {
            'Content-Type': 'application/json'
        }
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`Error en la creación de la cuenta: ${errorText}`);
    }

    return fetchUserData(); // Refrescar datos del usuario
}

// Nueva función: Obtener las cuentas del usuario desde localStorage
export function getUserAccounts(filterStatus) {
    const user = getLocalData('user');
    if (!user || !user.bankAccounts) return [];

    return user.bankAccounts.filter(account =>
        filterStatus === 3
            ? account.status === 'ACTIVE' || account.status === 'SUSPENDIDA'
            : true
    );
}

// Nueva función: Eliminar una cuenta bancaria (si el backend lo permite)
export async function deleteAccount(accountNumber) {
    const response = await fetch(`${API_ACCOUNTS_URL}/${accountNumber}`, {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' }
    });

    if (!response.ok) throw new Error('Error al eliminar la cuenta');

    console.log('Cuenta eliminada exitosamente');
    return fetchUserData(); // Actualizar usuario después del cambio
}
