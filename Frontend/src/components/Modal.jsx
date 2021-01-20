import styles from './Modal.module.css';
import Backdrop from './Backdrop';

const Modal = (props) => (
    <>
        <Backdrop show={props.show} clicked={props.modalClosed} />
        <div className={styles.Modal}
            style={{
                transform: props.show ? 'translateY(0)' : 'translateY(-100vh)',
                opacity: props.show ? '1' : '0'
            }}>
            {props.children}
        </div>
    </>
);

export default Modal;