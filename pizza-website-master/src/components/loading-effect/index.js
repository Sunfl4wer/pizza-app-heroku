import React from 'react';
import Loader from 'react-loader-spinner';
import './loading.sass'

export default class Loading extends React.Component {

    render() {
        return (
            <div className="loader-container">
                <Loader visible={true} type="TailSpin" color="rgb(82, 82, 82)" height={70} width={70}  />
            </div>
        )
    }
}