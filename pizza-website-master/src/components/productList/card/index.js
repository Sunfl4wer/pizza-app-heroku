import React from 'react';
import './card.sass';
import { Link } from 'react-router-dom';
import Dropdown from '../../config-dropdown';
import PropTypes from 'prop-types';

class Card extends React.Component {

    sizeChanged = (index) => {
        this.props.setSize({
            "id": this.props.product.id, 
            "sizeID": index
        });
    }

    optionChanged = (index) => {
        this.props.setOption({
            "id": this.props.product.id, 
            "optionID": index
        });
    }

    addToCart = () => {
        let { id, name, imageURL, pricing, additionalOption, category } = this.props.product;

        this.props.addToCart({
            "id": id,
            "name": name,
            "imageURL": imageURL,
            "pricing": pricing,
            "additionalOption": additionalOption,
            "category": category
        });
    }

    render() {
        let { additionalOption, pricing, imageURL, name, description, id, category } = this.props.product;
        let price, config;

        if (category === "PIZZA"){

            if (additionalOption.selected !== null) {
                price = additionalOption.price[additionalOption.selected]
                        + pricing.price[pricing.selected]
            } else {
                price = pricing.price[pricing.selected]
            }
                
            config = <Dropdown size={pricing} sizeChanged={this.sizeChanged} 
                        optionChanged={this.optionChanged} option={additionalOption}/>

        } else {
            price = pricing
        }
        

        return (
            <div className="card">
                <img className="image" src={imageURL} alt="product"/>

                <div className="info">
                    <div className="productName">
                        {name}
                    </div>

                    <div className="price">
                        {`${new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price)}`}
                    </div>

                    <div className="description">
                        {description}
                    </div>
                    <Link className="detail" to={`/detail/${id}`}>More detail</Link>
                    <div className="cta">
                        {config}

                        <button onClick={ () => this.addToCart() } className="add" >Add to cart</button>
                    </div>
                </div>
            </div>
        )
    }
}

Card.propTypes = {
    setOption: PropTypes.func,

    setSize: PropTypes.func,

    addToCart: PropTypes.func,

    product: PropTypes.shape({
        id: PropTypes.string,
        name: PropTypes.string,
        imageURL: PropTypes.string,
        description: PropTypes.string,

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
            selected: PropTypes.number
        }),

        category: PropTypes.string
    })
}

export default Card;