import React from 'react';
import ReactDOM from 'react-dom';

import Backdrop from '../components/Backdrop';
import InvalidTokenModal from '../components/InvalidTokenModal';
import Modal from '../components/Modal';
import SearchedUser from '../components/SearchedUser';



    it('Test if Backdrop renders', () => {
        const div = document.createElement('div');
        ReactDOM.render(<Backdrop/>, div);
        ReactDOM.unmountComponentAtNode(div);

    });

    it('Test if InvalidTokenModal renders', () => {
        const div = document.createElement('div');
        ReactDOM.render(<InvalidTokenModal/>, div);
        ReactDOM.unmountComponentAtNode(div);

    });

    it('Test if Modal renders', () => {
        const div = document.createElement('div');
        ReactDOM.render(<Modal/>, div);
        ReactDOM.unmountComponentAtNode(div);

    });

    it('Test if SearchedUser renders', () => {
        const div = document.createElement('div');
        ReactDOM.render(<SearchedUser searcheduser={{
            username:"test",
        }}/>, div);
        ReactDOM.unmountComponentAtNode(div);

    });
