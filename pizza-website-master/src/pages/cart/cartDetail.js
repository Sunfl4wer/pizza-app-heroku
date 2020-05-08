import React from 'react';
import './cartDetail.sass'
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';

class CartDetail extends React.Component {

    componentDidMount() {
        let inputs = document.getElementsByTagName('input');
        
        if (inputs.length !== 0) {
            for (let i = 0; i < inputs.length; i++) {
                inputs[i].addEventListener('click', () => {
                    inputs[i].select();
                })
            }
        }
    }

    render() {
        let items = this.props.items;

        if (items.length === 0) {
            return (
                <div className="box">
                    <div className="title">Shopping Cart</div>

                    <div className="cart-container">
                        <div className="empty">Your cart is empty.</div>
                    </div>
                </div>
            )
        } 
        else {
            let totalPrice = 0, price = 0, content = [], inputs = [];
            let { increaseQuantity, decreaseQuantity, setQuantity, removeFromCart } = this.props;

            for (let i = 0; i < items.length; i++) {
                if (items[i].category !== "PIZZA") {
                    price = items[i].pricing * items[i].quantity;
                    totalPrice += price;
                } else {
                    price = (items[i].pricing.price[items[i].pricing.selected] 
                        + items[i].additionalOption.price[items[i].additionalOption.selected]) 
                        * items[i].quantity;
                    totalPrice += price;
                }

                inputs.push(
                    <input id={items[i].index} type="number" 
                        onChange={ e => setQuantity(parseInt(e.target.id), e.target.value)} 
                        value={items[i].quantity} />
                )

                content.push(
                    <tr className="product-row" key={items[i].index}>
                        <td className="delete">
                            <img alt="remove" onClick={ () => removeFromCart(items[i].index) } className="trash" src={require(`../../assets/images/trash.png`)}/>
                        </td>
                        
                        <td className="image">
                            <Link to={`/detail/${items[i].id}`}>
                                <img alt="product" src={items[i].imageURL}/>
                            </Link>
                        </td>
                        
                        <td className="name">
                            {items[i].name}
                            {items[i].category === "PIZZA" ? <div>
                                <span>Size: {items[i].pricing.size[items[i].pricing.selected]}</span>
                                <span>Option: {items[i].additionalOption.option[items[i].additionalOption.selected]}</span>
                            </div>
                                : null}
                            
                        </td>

                        <td className="quantity">
                            <img alt="plus" className="plus" onClick={ () => increaseQuantity(items[i].index) } src={require(`../../assets/images/plus.png`)}/>
                            
                            {inputs[i]}

                            <img alt="minus" className="minus" onClick={ items[i].quantity !== 1 ? 
                                () => decreaseQuantity(items[i].index) : 
                                () => removeFromCart(items[i].index) } src={require(`../../assets/images/minus.png`)}/>
                        </td>

                        <td className="price">{`${new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price)}`}</td>
                    </tr>
                )
            };

            return (
                <div className="box">
                    <div className="title">Shopping Cart</div>
    
                    <div className="cart-container">
                        <table className="product-list">
                            <tbody>
                                {content}
                            </tbody>
                        </table>
                        
                        <div className="total">
                            <div>Total: <span>{`${new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(totalPrice)}`}</span></div>
                            <Link to="/checkout" ><button className="cta">PROCEED TO CHECKOUT</button></Link>
                        </div>
                    </div>
                </div>
            )
        }
    }
}

CartDetail.propTypes = {
    items: PropTypes.arrayOf(PropTypes.shape({
        index: PropTypes.number,
        id: PropTypes.string,
        name: PropTypes.string,
        imageURL: PropTypes.string,

        pricing: PropTypes.oneOfType([
            PropTypes.shape({
                size: PropTypes.arrayOf(PropTypes.string),
                price: PropTypes.arrayOf(PropTypes.number),
                selected: PropTypes.number
            }),
            PropTypes.number
        ]),

        additionalOption: PropTypes.shape({
            option: PropTypes.arrayOf(PropTypes.string),
            price: PropTypes.arrayOf(PropTypes.number),
            selected: PropTypes.number,
        }),

        category: PropTypes.string,
        quantity: PropTypes.number
    }))
}

export default CartDetail;