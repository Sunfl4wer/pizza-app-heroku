import React from 'react';
import "./detail.sass";
import PropTypes from 'prop-types';
import Config from '../../components/config-dropdown';

class Detail extends React.Component {

    sizeChanged = sizeID => {
        this.props.setSize(sizeID);
    }

    optionChanged = optionID => {
        this.props.setOption(optionID);
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
        })
    }

    render() {
        let price, config;
        let { name, imageURL, description, pricing, additionalOption, category, ingredients, servingSize } = this.props.product;

        if (category === "PIZZA"){
            if (additionalOption.selected !== null) {
                price = additionalOption.price[additionalOption.selected]
                        + pricing.price[pricing.selected]
            } else {
                price = pricing.price[pricing.selected]
            }
                
            config = <Config sizeChanged={this.sizeChanged} optionChanged={this.optionChanged} 
                            size={pricing} option={additionalOption}/>
        } else {
            price = pricing
        }

        return (
            <div className="container">

                <div className="name">
                    {name}
                </div>

                <div className="row">
                    <img alt="product" src={imageURL} className="image" />
                    
                    <div className="info">
                        <div className="description">
                            {description}
                        </div>

                        <div className="ingredients">
                            <b>Ingredients:</b> {ingredients}
                        </div>

                        
                        {servingSize ?  <div className="serving">
                                            <b>Serving size:</b> {servingSize}
                                        </div> : null}
                    </div>
                </div>

                {config}

                <div className="cta">
                    <p>Price: {`${new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price)}`}</p>
                    <button onClick={ () => this.addToCart() }>Add to cart</button>
                </div>
            </div>
        )
    }
}

Detail.propTypes = {
    setSize: PropTypes.func,
    setOption: PropTypes.func,
    addToCart: PropTypes.func,
    product: PropTypes.shape({
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
            selected: PropTypes.number,
        }),

        category: PropTypes.string
    })
}

export default (Detail);