import React from 'react';
import './header.sass'
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { sign_out } from '../../pages/sign-in/store/userSlice';
import { setPreviousPage, resetPreviousPage } from '../../feature/previousPage/previousPageSlice';
import PropTypes from 'prop-types';
import Message from '../message-box';
import { setMessage } from '../../feature/message/messageSlice';

class Header extends React.Component {
    constructor(props) {
        super(props);
        this.navRef = React.createRef();
        this.burger = React.createRef()
    }

    sign_out = () => {
        this.props.dispatch(sign_out());
        this.props.dispatch(setMessage("You are signed out."))
    }

    componentDidMount() {
        this.burger.current.addEventListener('click', () => {
            this.navRef.current.classList.toggle('right-active')
        })
    }

    setPreviousPage = () => {
        if (window.location.pathname !== '/sign-in' && window.location.pathname !== '/sign-up'){
            this.props.dispatch(resetPreviousPage());
            this.props.dispatch(setPreviousPage(window.location.pathname));
        }
    }
    

    render() {
        let { code, data } = this.props.user;
        let { cart } = this.props;
        let content, cartLength = 0, message = null;

        cart.forEach(item => {
            cartLength += item.quantity;
        });
        if (this.props.message !== '') {
            message = <Message />
        }

        if (code !== 200) {
            content = <div>
                        {message}
                        <header>
                            <div className="left">
                                <Link to="/"><img className="logo" src={require("../../assets/images/logo.png")} alt="logo" /></Link>
                                <Link to="/"><h3 className="name" >PIZZA SOHU</h3></Link>
                            </div>

                            <div className="right" ref={this.navRef}>
                                <Link className="cta" to="/sign-in" onClick={ () => this.setPreviousPage()} >Sign in</Link>
                                <span>-</span>
                                <Link  className="cta" to="/sign-up" onClick={ () => this.setPreviousPage()} >Sign up</Link>
                                <label >Your cart: </label>
                                <Link to="/cart" onClick={ () => this.setPreviousPage()} >
                                    <img className="cart" src={require("../../assets/images/supermarket.png")} alt="cart" />
                                    <div className="counter">
                                        <p>{cartLength}</p>
                                    </div>
                                </Link>
                            </div>

                            <div className="burger" ref={this.burger}>
                                <div className="line1"></div>
                                <div className="line2"></div>
                                <div className="line3"></div>
                            </div>
                        </header>
                    </div>
        } else {
            content = <div>
                        {message}
                        <header>
                            <div className="left">
                                <Link to="/"><img className="logo" src={require("../../assets/images/logo.png")} alt="logo" /></Link>
                                <Link to="/"><h3 className="name" >PIZZA SOHU</h3></Link>
                            </div>

                            <div className="right" ref={this.navRef}>
                                <div className="name">Hello {data.firstName}</div>
                                <span>-</span>
                                <Link className="cta" to="#" onClick={this.sign_out}>Sign out</Link>
                                <label >Your cart: </label>
                                <Link to="/cart" onClick={ () => this.setPreviousPage()} >
                                    <img className="cart" src={require("../../assets/images/supermarket.png")} alt="cart" />
                                    <div className="counter">
                                        <p>{cartLength}</p>
                                    </div>
                                </Link>
                            </div>
                            <div className="burger" ref={this.burger}>
                                <div className="line1"></div>
                                <div className="line2"></div>
                                <div className="line3"></div>
                            </div>
                        </header>
                    </div>
        }

        return (
            <div>
                {content}
            </div>
        )
    }
}

Header.propTypes = {
    user: PropTypes.shape({
        data: PropTypes.shape({
            firstName: PropTypes.string,
            lastName: PropTypes.string,
        }),
        code: PropTypes.number
    })
}

const mapStatetoProps  = state => {
    return {
        user: state.user,
        message: state.message,
        cart: state.cart
    }
}
export default connect(mapStatetoProps)(Header);