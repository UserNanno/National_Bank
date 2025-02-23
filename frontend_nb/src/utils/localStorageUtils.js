export function getLocalData(key) {
    const data = localStorage.getItem(key);
    return data ? JSON.parse(data) : null;
}

export function setLocalData(key, value) {
    localStorage.setItem(key, JSON.stringify(value));
}

export function removeLocalData(key) {
    localStorage.removeItem(key);
}
