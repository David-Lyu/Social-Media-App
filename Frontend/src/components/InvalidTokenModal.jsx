import React, { useEffect } from 'react';
import Modal from './Modal';

const InvalidTokenModal = () => {

    useEffect(() => {
        setTimeout(() => {
            window.location.href = "/";
        }, 3000)
    }, []);

    return (
        <div className="container">
            <Modal show={true} modalClosed={null}>
                <h3 className="text-center">Token invalid</h3>
            </Modal>
        </div>);
};

export default InvalidTokenModal;