import React from 'react';
import Header from '../../components/header';
import Payment from './payment';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { setPreviousPage } from '../../feature/previousPage/previousPageSlice';
import { setMessage } from '../../feature/message/messageSlice';
import { Redirect } from 'react-router-dom';
import { reset } from '../cart/store/cartSlice';
import CartSumary from './cartSumary';
import Popup from './popup/index';
import './payment.sass';

class Checkout extends React.Component {

    submit = () => {
        this.props.dispatch(setMessage("Order submitted successful. Thank you!!"));
        this.props.dispatch(reset());
        window.location.replace('/');
    }

    render() {
        let { cart, user } = this.props;

        if (user.code !== 200) {
            this.props.dispatch(setPreviousPage('/checkout'));
            this.props.dispatch(setMessage("Please sign in with your account first"));
            return (
                <Redirect push to='/sign-in' />
            )
        }
        
        return (
            <div>
                <Header  />
                <Popup submit={this.submit} />
                <div className="payment-container">
                    <Payment user={user.data} placeOrder={this.placeOrder} />
                    <CartSumary cart={cart} />
                </div>
                
            </div>
        )
    }
    
}

Checkout.propTypes = {
    cart: PropTypes.arrayOf(PropTypes.object),
    user: PropTypes.object
}

const mapState = state => {
    return {
        cart: state.cart,
        user: state.user
    }
}

export default connect(mapState)(Checkout);