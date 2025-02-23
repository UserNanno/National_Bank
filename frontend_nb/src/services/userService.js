import { getLocalData, setLocalData } from '../utils/localStorageUtils';

const API_BASE_URL = 'http://localhost:8080/api/users';

export async function fetchUserData() {
    const userId = getLocalData('userId');
    if (!userId) throw new Error('No userId found in localStorage');

    const response = await fetch(`${API_BASE_URL}/${userId}`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' }
    });

    if (!response.ok) throw new Error('Error al obtener los datos del usuario');

    const userData = await response.json();
    setLocalData('user', userData);
    return userData;
}
