import React from 'react';
import Card from './card';
import './productList.sass';
import PropTypes from 'prop-types';

class ProductList extends React.Component {

    render() {
        let { productList, setSize, setOption, addToCart } = this.props
        return(
            <div className="productList">
                {productList.map( product => 
                    <Card addToCart={addToCart} setSize={setSize} setOption={setOption} product={product} key={product.id}/>
                )}
            </div>
        )
    }
}

ProductList.propTypes = {
    productList: PropTypes.arrayOf(PropTypes.object),
    setSize: PropTypes.func,
    setOption: PropTypes.func,
    addToCart: PropTypes.func
}

export default (ProductList);