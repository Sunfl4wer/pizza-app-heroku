import React from 'react';
import Header from '../../components/header';
import fetchData from './services/fetchData';
import Detail from './detail';
import { connect } from 'react-redux';
import Loading from '../../components/loading-effect';
import { setSize, setOption} from './store/detailSlice';
import PropTypes from 'prop-types';
import { addToCart, save } from '../cart/store/cartSlice';
import { setMessage } from '../../feature/message/messageSlice';

class Container extends React.Component {
    constructor(props) {
        super(props);
        this.productID = this.props.match.params.id;
    }

    componentDidMount() {
        this.props.dispatch(fetchData(this.productID))
    }

    setSize = sizeID => {
        this.props.dispatch(setSize(sizeID));
    }

    setOption = optionID => {
        this.props.dispatch(setOption(optionID));
    }

    addToCart = item => {
        let { name } = item;
        this.props.dispatch(addToCart(item));
        this.props.dispatch(setMessage(`Added ${name} to cart.`))
        this.props.dispatch(save());
    }
    
    render() {
        let { product, loading } = this.props.productDetail;

        if (loading) {
            return(
                <div>
                    <Header />
                    <Loading />
                </div>
            )
        } else {
            if (product !== undefined){
                return(
                <div>
                    <Header />
                    <Detail addToCart={this.addToCart} setSize={this.setSize} setOption={this.setOption} product={product} />
                </div>
                )
            } else {
                return (
                    <div>
                        <Header />
                        <Loading />
                    </div>
                )
            }
        }
        
    }
}

Container.propTypes = {
    productDetail: PropTypes.shape({
        loading: PropTypes.bool,
        product: PropTypes.object
    })
}

const mapState = state => {
    return {
        productDetail: state.productDetail
    }
}

export default connect(mapState)(Container);