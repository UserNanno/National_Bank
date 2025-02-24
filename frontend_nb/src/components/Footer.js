import React from 'react';
import '../styles/Footer.css';

export default function Footer() {
    return (
        <footer className="footer" role="contentinfo">
            © {new Date().getFullYear()} National Bank
        </footer>
    );
}
