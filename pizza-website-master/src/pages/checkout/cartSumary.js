import React from 'react';
import { Link } from 'react-router-dom';

class CartSumary extends React.Component {
    render() {
        let { empty, cart } = this.props;
        let cartTable;

        if (empty) {
            cartTable = 
                <div className="summary">
                    <div className="empty">Your cart is empty.</div>
                </div>
        } else {
            let price = 0;
            
            cart.forEach( item => {
                if (item.category === "PIZZA") {
                    price += (item.pricing.price[item.pricing.selected]
                            + item.additionalOption.price[item.additionalOption.selected]) * item.quantity;
                } else {
                    price += item.pricing * item.quantity;
                }
            });

            cartTable = 
            <div>
                <div className="cart">
                    <div className="title">Cart summary <span>[<Link to="/cart">Edit</Link>]</span></div>
                    <table className="product-list">
                        <tbody>

                            {cart.map( item => {
                                let { id, name, quantity, category, pricing, additionalOption } = item;
                                return (<tr key={id} className="product-row">
                                    <td className="number">{quantity} X </td>
                                    <td className="name">
                                        {name}
                                        {category === "PIZZA" ? 
                                            <div>
                                                <span>Size: {pricing.size[pricing.selected]}</span>
                                                <span>Option: {additionalOption.option[additionalOption.selected]}</span>
                                            </div> : null
                                        }
                                    </td>
                                    <td className="price">
                                        {category === "PIZZA" ?
                                            pricing.price[pricing.selected] + additionalOption.price[additionalOption.selected]
                                            : pricing}
                                    </td>
                                </tr>
                            )})}
                            
                        </tbody>
                    </table>
                </div>
            
                <div className="total">
                    <div>Total: <span>{price}</span></div>
                    <div className="currentcy">VND</div>
                </div>
            </div>
        }
        return (
            <div className="summary">{cartTable}</div>
        )
    }
}

export default CartSumary;