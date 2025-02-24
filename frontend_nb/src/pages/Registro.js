import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/Registro.css';
import logo from '../images/logo.png';
import { registerUser } from '../services/userService';

export default function Registro() {
    const [formData, setFormData] = useState({
        documentType: 'DNI',
        dni: '',
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
        birthDate: '',
        password: '',
        passwordConfirmed: ''
    });

    const [errors, setErrors] = useState([]);
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const validateForm = () => {
        const newErrors = [];

        if (!/^\d{8}$/.test(formData.dni) && formData.documentType === 'DNI') {
            newErrors.push('El DNI debe tener 8 dígitos.');
        } else if (!/^[a-zA-Z0-9]{9}$/.test(formData.dni) && formData.documentType === 'Carnet de Extranjeria') {
            newErrors.push('El Carnet de Extranjería debe tener 9 caracteres alfanuméricos.');
        }

        if (!formData.firstName) newErrors.push('El nombre es obligatorio.');
        if (!formData.lastName) newErrors.push('Los apellidos son obligatorios.');
        if (!/^[\w.-]+@[\w.-]+\.[a-zA-Z]{2,}$/.test(formData.email)) newErrors.push('El correo electrónico no es válido.');
        if (!/^\d{9}$/.test(formData.phone)) newErrors.push('El celular debe tener 9 dígitos.');
        if (!isValidAge(formData.birthDate)) newErrors.push('Debes ser mayor o igual a 18 años.');
        if (formData.password.length < 6) newErrors.push('La clave web debe tener al menos 6 caracteres.');
        if (formData.password !== formData.passwordConfirmed) newErrors.push('Las claves no coinciden.');

        setErrors(newErrors);
        return newErrors.length === 0;
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (!validateForm()) return;

        try {
            await registerUser(formData);
            alert('Usuario registrado correctamente.');
            navigate('/');
        } catch (error) {
            setErrors([error.message]);
        }
    };

    const isValidAge = (birthDate) => {
        const today = new Date();
        const birth = new Date(birthDate);
        let age = today.getFullYear() - birth.getFullYear();
        const monthDiff = today.getMonth() - birth.getMonth();
        if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birth.getDate())) {
            age--;
        }
        return age >= 18;
    };

    return (
        <div className='registro__container'>
            <div className='registro__card'>
                <img src={logo} alt="logo" className='registro__logo' />
                <form className='form__registro' onSubmit={handleSubmit}>
                    <div>
                        <select name="documentType" value={formData.documentType} onChange={handleChange} className='registro__documentType'>
                            <option value="DNI">DNI</option>
                            <option value="Carnet de Extranjeria">Carnet de Extranjeria</option>
                        </select>

                        <input type="text" name="dni" placeholder="Número de documento" value={formData.dni} onChange={handleChange} className='registro__dni' />

                        <input type="text" name="firstName" placeholder="Nombres completos" value={formData.firstName} onChange={handleChange} className='registro__input' />

                        <input type="text" name="lastName" placeholder="Apellidos completos" value={formData.lastName} onChange={handleChange} className='registro__input' />

                        <input type="email" name="email" placeholder="Correo electrónico" value={formData.email} onChange={handleChange} className='registro__input' />

                        <div>
                            <input type="text" name="phone" placeholder="Celular" value={formData.phone} onChange={handleChange} className='registro__celular' />

                            <input
                                type="date"
                                name="birthDate"
                                value={formData.birthDate}
                                onChange={handleChange}
                                className='registro__fechaNacimiento'
                            />
                        </div>

                        <input type="password" name="password" placeholder="Clave web" value={formData.password} onChange={handleChange} className='registro__input' />

                        <input type="password" name="passwordConfirmed" placeholder="Confirmar clave web" value={formData.passwordConfirmed} onChange={handleChange} className='registro__input' />

                        {errors.length > 0 && (
                            <ul className="registro__errorMessage">
                                {errors.map((err, index) => (
                                    <li key={index}>{err}</li>
                                ))}
                            </ul>
                        )}

                        <button type="submit" className='registro__boton'>Registrar</button>
                    </div>
                </form>
            </div>
        </div>
    );
}
