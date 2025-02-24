import React, { useState } from 'react';
import '../styles/DatosPersonales.css';
import { getLocalData } from '../utils/localStorageUtils';

function DatosPersonales() {
    const user = getLocalData('user');

    const [userData] = useState({
        firstName: user?.firstName || '',
        lastName: user?.lastName || '',
        phone: user?.phone || '',
        numIdentification: user?.numIdentification || '',
        birthDate: user?.birthDate || '',
        documentType: user?.documentType || 'DNI'
    });

    return (
        <div id="datos-personales">
            <h2>Información Personal</h2>
            <form className="formDatos">
                <div>
                    <label htmlFor="dni">Tipo Documento</label>
                    <select id="dni" value={userData.documentType} disabled>
                        <option value="DNI">DNI</option>
                        <option value="CE">Carnet de Extranjería</option>
                    </select>

                    <label htmlFor="nombres">Nombres</label>
                    <input id="nombres" type="text" value={userData.firstName} disabled />

                    <label htmlFor="celular">Celular</label>
                    <input id="celular" type="tel" value={userData.phone} disabled />
                </div>

                <div>
                    <label htmlFor="numero-documento">Número de Documento</label>
                    <input id="numero-documento" type="text" value={userData.numIdentification} disabled />

                    <label htmlFor="apellidos">Apellidos</label>
                    <input id="apellidos" type="text" value={userData.lastName} disabled />

                    <label htmlFor="fecha-nacimiento">Fecha de Nacimiento</label>
                    <input id="fecha-nacimiento" type="date" value={userData.birthDate} disabled />
                </div>
            </form>
        </div>
    );
}

export default DatosPersonales;
