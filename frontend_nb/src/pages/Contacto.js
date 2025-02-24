import React, { useState } from 'react';
import '../styles/Contacto.css';

// Importación directa de imágenes
import fondoContactanos from '../images/fondo-contactanos.png';
import logoBlanco from '../images/logo-blanco.png';
import mapaImg from '../images/maps.png';

function Contacto() {
    const [sugerencia, setSugerencia] = useState('');

    const handleSubmit = () => {
        if (!sugerencia.trim()) {
            alert('Por favor, ingrese una sugerencia antes de enviar.');
            return;
        }

        alert('Gracias por tu sugerencia. La hemos recibido correctamente.');
        setSugerencia('');
    };

    return (
        <div id="contactanos">

            <section className="inicio">
                <div className="imagen_con_texto">
                    <img className="fondo-contactanos" src={fondoContactanos} alt="Fondo de contacto" />
                    <div className="cuadro_con_texto">
                        <p>
                            En National Bank, estamos aquí para ayudarte en todo lo que necesites. Si tienes alguna pregunta o necesitas asistencia, estamos disponibles para ti:
                        </p>
                        <ol className="contacto">
                            <li>📞 <strong>Teléfono:</strong> Llámanos al <a href="tel:014006000">01-400-6000</a> de lunes a viernes de 9:00 a.m. a 6:00 p.m.</li>
                            <li>📧 <strong>Correo electrónico:</strong> Envía un correo a <a href="mailto:national.banck@gmail.com">national.banck@gmail.com</a> y te responderemos en un plazo máximo de 24 horas.</li>
                        </ol>
                    </div>
                </div>
                <header className="header">
                    <div className="logoBlanco">
                        <img src={logoBlanco} alt="Logo blanco de National Bank" />
                    </div>
                </header>
            </section>

            <main className="main">
                <div className="visita">
                    <div className="info-visita">
                        <h4>Visítanos</h4>
                        <p>Si prefieres una atención cara a cara, te invitamos a visitar nuestra sucursal ubicada en la Avenida Javier Prado, San Isidro, Lima.</p>
                    </div>
                    <div className="mapa">
                        <img src={mapaImg} alt="Mapa de la ubicación de la sucursal" />
                    </div>
                </div>

                <div className="formulario">
                    <h4>Formulario de sugerencias</h4>
                    <label htmlFor="sugerencias">Déjanos tu sugerencia:</label>
                    <textarea
                        className="sugerencias"
                        id="sugerencias"
                        name="sugerencias"
                        rows="5"
                        placeholder="Escribe aquí tus sugerencias..."
                        value={sugerencia}
                        onChange={(e) => setSugerencia(e.target.value)}
                    />
                    <button type="button" className="enviar" onClick={handleSubmit}>Enviar</button>
                </div>
            </main>

        </div>
    );
}

export default Contacto;
