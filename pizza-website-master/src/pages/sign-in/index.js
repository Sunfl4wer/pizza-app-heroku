import React from 'react';
import Header from '../../components/header';
import { connect } from 'react-redux';
import Form from './form';
import Loading from '../../components/loading-effect';
import sign_in from './services/sign-in';
import { Redirect } from 'react-router-dom';
import PropTypes from 'prop-types';
import { setMessage } from '../../feature/message/messageSlice';

class SignIn extends React.Component {
    signIn = (phoneNumber, password) => {
        this.props.dispatch(sign_in(phoneNumber, password));
    }

    render() {
        let content;
        let form = <Form signIn={this.signIn} />
        let { user, previousPage, dispatch } = this.props;
        
        switch (user.code) {
            case 200:
                dispatch(setMessage("Signed in successful"))

                if (previousPage === '') {
                    content = <Redirect push to='/' />
                } else {
                    content = <Redirect push to={previousPage} />
                }
                
                break;
            case 401:
                dispatch(setMessage("Wrong phone number or password."));

                content = 
                    <div>
                        <Header />
                        {form}
                    </div>
                break;
            default:
                if (user.loading) {
                    content = 
                        <div>
                            <Header />
                            <Loading />
                        </div>
                } else {
                    content = 
                        <div>
                            <Header />
                            {form}
                        </div>
                }
                break;
        }

        return (
            <div>
                {content}
            </div>
        )
    }
}

SignIn.propTypes = {
    user: PropTypes.shape({
        loading: PropTypes.bool,
        data: PropTypes.object,
        code: PropTypes.number
    }),

    previousPage: PropTypes.string
}

const mapState = state => {
    return {
        user: state.user,
        previousPage: state.previousPage,
    }
}

export default connect(mapState)(SignIn);