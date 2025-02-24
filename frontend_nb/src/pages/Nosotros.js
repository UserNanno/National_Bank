import React from 'react';
import '../styles/Nosotros.css';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';

// Importación directa de imágenes
import fondoBancoNosotros from '../images/fondoBancoNosotros.png';
import logoBancoOscuro from '../images/logoBancoOscuro.png';
import logoHistoria from '../images/logoHistoria.png';
import logoMision from '../images/logoMision.png';
import logoVision from '../images/logoVision.png';
import people1 from '../images/people1.png';
import people2 from '../images/people2.png';
import people3 from '../images/people3.png';

// Componente reutilizable para bloques informativos
const BloqueInformativo = ({ imgSrc, titulo, contenido }) => (
    <aside className="bloqueInferior">
        <article className="art">{contenido}</article>
        <aside className="cuadro-imagen-titulo">
            <div className="imgContenido">
                <img src={imgSrc} alt={titulo} />
            </div>
            <div className="contenido-nombre">
                <p className="nombre">{titulo}</p>
            </div>
        </aside>
    </aside>
);

export default function Nosotros() {
    return (
        <div id="nosotros">
            <Navbar />

            <section className="inicio">
                <div className="franja"></div>
                <div className="imagen_con_texto">
                    <img src={fondoBancoNosotros} alt="Edificio del banco" />
                    <div className="cuadro_con_texto">
                        <p>
                            En National Bank, nos enorgullecemos de ser una institución
                            financiera sólida y confiable con una larga trayectoria de servicio
                            a nuestras comunidades. Desde nuestra fundación, hemos estado
                            comprometidos con brindar soluciones financieras innovadoras y
                            personalizadas que ayuden a nuestros clientes a alcanzar sus metas.
                        </p>
                    </div>
                </div>
                <header className="header">
                    <div className="logoOscuro">
                        <img src={logoBancoOscuro} alt="Logo de National Bank en oscuro" />
                    </div>
                </header>
            </section>

            <section className="centro">
                <p className="nombre"> NATIONAL BANK </p>
                <hr className="barraIzquierda" />
                <hr className="barraDerecha" />
                <p className="lema">Tu aliado financiero de confianza</p>
            </section>

            <main className="main">
                <BloqueInformativo
                    imgSrc={logoHistoria}
                    titulo="HISTORIA"
                    contenido="National Bank tiene una larga y rica historia que se remonta al año 2000.
            Desde nuestros humildes comienzos como una pequeña institución financiera local,
            hemos crecido hasta convertirnos en un banco sólido y confiable con una presencia
            significativa en el ámbito nacional."
                />
                <BloqueInformativo
                    imgSrc={logoMision}
                    titulo="MISIÓN"
                    contenido="Nuestra misión es ser el banco preferido de nuestros clientes, ofreciendo un servicio excepcional
            y productos financieros que satisfagan sus necesidades únicas. Nos comprometemos a brindar
            soluciones financieras innovadoras y personalizadas."
                />
                <BloqueInformativo
                    imgSrc={logoVision}
                    titulo="VISIÓN"
                    contenido="Aspiramos a ser el banco líder en el mercado, reconocido por su excelencia en el servicio al cliente,
            su innovación financiera y su compromiso con el desarrollo sostenible."
                />
            </main>

            <section className="people">
                {[
                    { img: people1, title: 'CEO', name: 'Luz Estrada Martinez' },
                    { img: people2, title: 'CFO', name: 'Carlos Álvarez Días' },
                    { img: people3, title: 'DIRECTOR DE TECNOLOGÍA', name: 'Adrian Hernández Navarro' }
                ].map((person, index) => (
                    <div key={index} className="contenedor-people">
                        <div className="img">
                            <img src={person.img} alt={person.title} />
                        </div>
                        <hr />
                        <div className="nombre-people">
                            <strong><p>{person.title}</p></strong>
                            <p>{person.name}</p>
                        </div>
                    </div>
                ))}
            </section>

            <Footer />
        </div>
    );
}
