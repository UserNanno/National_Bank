import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/Inicio.css';
import logo from '../images/logo.png';
import { setLocalData } from '../utils/localStorageUtils';
import { loginUser } from '../services/userService';

export default function Inicio() {
    const [documentType, setDocumentType] = useState('DNI');
    const [dni, setDni] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();

    const validateForm = async (event) => {
        event.preventDefault();
        setErrorMessage('');

        if (!dni || !isValidDNI(documentType, dni)) {
            setErrorMessage('Número de documento inválido.');
            return;
        }

        if (!password) {
            setErrorMessage('La clave web es obligatoria.');
            return;
        }

        try {
            const user = await loginUser(dni, password);
            setLocalData('user', user);
            setLocalData('userId', user.id);
            navigate('/menu');
        } catch (error) {
            setErrorMessage(error.message);
        }
    };

    const isValidDNI = (documentType, dni) => {
        return (documentType === 'DNI' && /^\d{8}$/.test(dni)) ||
            (documentType === 'Carnet de Extranjeria' && /^[a-zA-Z0-9]{9}$/.test(dni));
    };

    return (
        <div className='login__container'>
            <div className='login__card'>
                <img src={logo} alt="logo" className='login__logo' />
                <form onSubmit={validateForm} className='form__login'>
                    <div>
                        <select
                            name="document-type"
                            value={documentType}
                            onChange={(e) => setDocumentType(e.target.value)}
                            className='login__documentType'
                            required
                        >
                            <option value="DNI">DNI</option>
                            <option value="Carnet de Extranjeria">Carnet de Extranjeria</option>
                        </select>

                        <input
                            type="text"
                            name="dni"
                            placeholder="Número de documento"
                            value={dni}
                            onChange={(e) => setDni(e.target.value)}
                            className='login__dni'
                            required
                        />
                    </div>

                    <input
                        type="password"
                        name="password"
                        placeholder="Clave web"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        className='login__password'
                        required
                    />

                    {errorMessage && <span className='login__errorMessage'>{errorMessage}</span>}

                    <button type="submit" className='login__ingresar'>Ingresar</button>

                    <button type="button" onClick={() => navigate('/registro')} className='login_registrar'>
                        Registrar
                    </button>
                </form>
            </div>
        </div>
    );
}
