const API_BASE_URL = 'http://localhost:8080/api/transactions';

export async function makeTransfer({ fromAccount, toAccount, amount }) {
    const user = getLocalData('user');
    if (!user) throw new Error('Usuario no autenticado');

    const response = await fetch(`${API_BASE_URL}/transfer`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            fromAccount: { accountNumber: fromAccount },
            toAccount: { accountNumber: toAccount },
            amount,
            userId: user.id
        })
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || 'Error en la transacción');
    }

    return response.json();
}
