import React from 'react';
import Header from '../../components/header';
import Loading from '../../components/loading-effect';
import Form from './form';
import { connect } from 'react-redux';
import postUser from './service/postUser';
import { Redirect } from 'react-router-dom';
import { setMessage } from '../../feature/message/messageSlice';
import { setUser, reset } from './store/signUpSlice';
import signIn from '../sign-in/services/sign-in';

class SignUp extends React.Component {

    postUser = (user) => {
        this.props.dispatch(reset())
        this.props.dispatch(postUser(user));
    }

    setUser = (user) => {
        this.props.dispatch(setUser(user));
    }

    componentWillUnmount() {
        if (this.props.signUp.user !== undefined) {
            this.props.dispatch(reset())
        }
    }

    render() {
        let { signUp, previousPage, dispatch } = this.props;
        let content;

        switch(signUp.code) {
            case 400:
                dispatch(setMessage("PHONE NUMBER or EMAIL already used."))

                content = 
                    <div>
                        <Header />
                        <Form postUser={this.postUser} setUser={this.setUser} />
                    </div>
                break;
            case 201:
                dispatch(setMessage("Sign up successful."));
                dispatch(signIn(signUp.user.username, signUp.user.password));

                if (previousPage !== '') {
                    content = <Redirect push to={previousPage} />;
                } else {
                    content = <Redirect push to='/' />
                }
                break;
            default:
                if (signUp.loading) {
                    content = 
                        <div>
                            <Header />
                            <Loading />
                        </div>
                } else {
                    content = 
                        <div>
                            <Header />
                            <Form postUser={this.postUser} setUser={this.setUser} />
                        </div>
                }
        }

        return (
            <div>
                {content}
            </div>
        )
    }
}

const mapState = state => {
    return {
        signUp: state.signUp,
        previousPage: state.previousPage,
    }
}

export default connect(mapState)(SignUp);