// utils/accountUtils.js
import { getLocalData } from './localStorageUtils';

export function filterAccountsByStatus(operation) {
    const user = getLocalData('user');
    if (!user || !user.bankAccounts) {
        console.error('No se encontraron cuentas bancarias para el usuario.');
        return [];
    }

    return user.bankAccounts.filter(account => {
        switch (operation) {
            case 1: return account.status === 'ACTIVE'; // Suspender
            case 2: return account.status === 'SUSPENDIDA'; // Reactivar
            case 3: return account.status === 'ACTIVE' || account.status === 'SUSPENDIDA'; // Cerrar
            default:
                console.error('Operación no válida.');
                return false;
        }
    });
}
