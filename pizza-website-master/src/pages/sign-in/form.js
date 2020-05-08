import React from 'react';
import { Link } from 'react-router-dom';
import { Formik, Field, Form } from 'formik';
import * as Yup from 'yup';
import './signIn.sass'

const signInSchema = Yup.object().shape({
    phoneNumber: Yup.number()
        .typeError('Only accept number type')
        .required('Required field'),
    password: Yup.string()
        .required('Required')
})

class SignInForm extends React.Component {

    render() {
        return (
            <div className="sign-in-container">

                <Formik 
                    initialValues={ {phoneNumber: '', password: ''} } 
                    validationSchema={signInSchema}
                    onSubmit={ (data) => {
                        this.props.signIn(data.phoneNumber, data.password);
                }}>
                    { ({ isSubmitting, errors, touched }) => (
                        <Form>
                            <div className="title">Member Sign in</div>
                            <Field name="phoneNumber" type="text" placeholder="Phone number" />
                            {errors.phoneNumber && touched.phoneNumber ? <div className="error">{errors.phoneNumber}</div> : null}

                            <Field name="password" type="password" placeholder="Password" />
                            {errors.password && touched.password ? <div className="error">{errors.password}</div> : null}

                            <button type="submit">Sign in</button>

                            <div className="cta">Don't have an account? <Link className="link" to="/sign-up">Sign up</Link></div>
                        </Form>
                    )}
                </Formik>

            </div>
        )
    }
}

export default SignInForm;