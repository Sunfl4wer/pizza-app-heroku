import React from 'react';
import { connect } from 'react-redux';
import Header from '../../components/header';
import Category from '../../components/category';
import Pagination from '../../components/pagination';
import ProductList from '../../components/productList';
import fetchData from './service/fetchData';
import { setCategory, category, setPage, setSize, setOption } from './store/productSlice';
import { addToCart, save } from '../cart/store/cartSlice';
import Loading from '../../components/loading-effect';
import PropTypes from 'prop-types';
import { setMessage } from '../../feature/message/messageSlice';

class Home extends React.Component {

    componentDidMount() {
        let { currentCategory, currentPage, size } = this.props.productList;
        this.props.dispatch(fetchData(currentCategory, currentPage, size));
    }

    setCategory = (cat) => {
        let { size } = this.props.productList;
        this.props.dispatch(setCategory(cat));
        this.props.dispatch(fetchData(cat, 1, size));
    }

    setPage = (page) => {
        let { currentCategory, size } = this.props.productList;
        this.props.dispatch(setPage(page));
        this.props.dispatch(fetchData(currentCategory, page, size));
    }

    setSize = (productID, size) => {
        this.props.dispatch(setSize(productID, size));
    }

    setOption = (productID, option) => {
        this.props.dispatch(setOption(productID, option));
    }

    addToCart = (product) => {
        this.props.dispatch(addToCart(product))
        this.props.dispatch(setMessage(`Added ${product.name} to cart.`))
        this.props.dispatch(save());
    }

    render() {
        let content, { currentCategory, currentPage, pageCount, data, loading } = this.props.productList;

        if (loading && data === []) {
            content = <Loading />
        } else {
            content = <div>
                <Category   setCategory={this.setCategory} 
                            currentCategory={currentCategory} 
                            category={category}/>

                <Pagination setPage={this.setPage} 
                            currentPage={currentPage} 
                            pageCount={pageCount} />
                            
                <ProductList    addToCart={this.addToCart} 
                                setSize={this.setSize} 
                                setOption={this.setOption} 
                                productList={data} />
            </div>
        }

        return (
            <div>
                <Header/>
                {content}                
            </div>
        )
    }
}

Home.propTypes ={
    productList: PropTypes.shape({
        currentCategory: PropTypes.string,
        currentPage: PropTypes.number,
        pageCount: PropTypes.number,
        loading: PropTypes.bool,
        list: PropTypes.arrayOf(PropTypes.object),
    })
}

const mapStatetoProps = state => {
    return {
        productList: state.productList,
    }
}

export default connect(mapStatetoProps)(Home);