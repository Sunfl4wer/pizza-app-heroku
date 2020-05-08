import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import Header from '../../components/header';
import CartDetail from './cartDetail';
import { increaseQuantity, decreaseQuantity, setQuantity, removeFromCart, save } from './store/cartSlice';

class Cart extends React.Component {

    increaseQuantity = index => {
        this.props.dispatch(increaseQuantity(index));
        this.props.dispatch(save());
    }

    decreaseQuantity = index => {
        this.props.dispatch(decreaseQuantity(index));
        this.props.dispatch(save());
    }

    setQuantity = (index, quantity) => {
        this.props.dispatch(setQuantity({
            index: index,
            quantity: quantity
        }));
        this.props.dispatch(save());
    }

    removeFromCart = index => {
        this.props.dispatch(removeFromCart(index));
        this.props.dispatch(save());
    }
    render() {
        return (
            <div>
                <Header />
                <CartDetail 
                increaseQuantity={this.increaseQuantity}  
                decreaseQuantity={this.decreaseQuantity}
                setQuantity={this.setQuantity}
                removeFromCart={this.removeFromCart}
                items={this.props.cart}/>
            </div>
        )
    }
}

Cart.propTypes = {
    cart: PropTypes.arrayOf(PropTypes.object)
}

const mapState = state => {
    return {
        cart: state.cart
    }
}

export default connect(mapState)(Cart);