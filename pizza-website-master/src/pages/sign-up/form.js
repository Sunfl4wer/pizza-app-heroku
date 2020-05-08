import React from 'react';
import { Formik, Field, Form } from 'formik';
import * as Yup from 'yup';
import './form.sass';

let initial = {
    "firstName": "",
    lastName: "",
    birthDay: "",
    email: "",
    phoneNumber: "",
    address: "",
    password: "",
    passwordConfirmation: ""
}

const signUpSchema = Yup.object().shape({
    firstName: Yup.string()
        .max(50, 'Too long!')
        .required('Required field'),
    lastName: Yup.string()
        .max(50, 'Too long!')
        .required('Required field'),
    birthDay: Yup.date()
        .required('Required field'),
    email: Yup.string()
        .email('Invalid email')
        .required('Required field'),
    phoneNumber: Yup.number()
        .typeError('Only accept number type')
        .required('Required field'),
    address: Yup.string()
        .required('Required field'),
    password: Yup.string()
        .min(8, 'Minimum 8 characters')
        .max(16, 'Maximum 16 characters')
        .required('Required field'),
    passwordConfirmation: Yup.string()
        .oneOf( [Yup.ref('password'), null ], 'Mismatch with password.')
})

class SignUpForm extends React.Component {
    render() {
        return (
            <div className="form-container">
                <Formik 
                    initialValues={ initial } 
                    validationSchema={signUpSchema}
                    onSubmit={ (data) => {
                        this.props.setUser({
                            "username": data.phoneNumber,
                            "password": data.password
                        })
                        this.props.postUser({
                            "username": data.phoneNumber,
                            "firstName": data.firstName,
                            "lastName": data.lastName,
                            "password": data.password,
                            "email": data.email,
                            "birthDate": data.birthDay,
                            "address": data.address
                        })
                }}>
                    { ({ errors, touched }) => (
                        <Form>
                            <div className="title">Member sign up</div>

                            <div className="section">
                                <div className="title">Personal Information</div>
                                <label>First name:</label>
                                <Field name="firstName"  type="text"/>
                                {errors.firstName && touched.firstName ? <div className="error">{errors.firstName}</div> : null}
                                <label>Last name:</label>
                                <Field name="lastName" type="text"/>
                                {errors.lastName && touched.lastName ? <div className="error">{errors.lastName}</div> : null}
                                <label>Birthday:</label>
                                <Field name="birthDay" type="date"/>
                                {errors.birthDay && touched.birthDay ? <div className="error">{errors.birthDay}</div> : null}
                            </div>

                            <div className="section">
                                <div className="title">Contact Information</div>
                                <label>Email:</label>
                                <Field name="email" type="text"/>
                                {errors.email && touched.email ? <div className="error">{errors.email}</div> : null}
                                <label>Phone number: </label>
                                <Field name="phoneNumber" type="text"/>
                                {errors.phoneNumber && touched.phoneNumber ? <div className="error">{errors.phoneNumber}</div> : null}
                                <label>Address:</label>
                                <Field name="address" type="text"/>
                                {errors.address && touched.address ? <div className="error">{errors.address}</div> : null}
                            </div>

                            <div className="section">
                                <div className="title">Passwords</div>
                                <label>Password:</label>
                                <Field name="password" type="password"/>
                                {errors.password && touched.password ? <div className="error">{errors.password}</div> : null}
                                <label>Re-enter password:</label>
                                <Field name="passwordConfirmation" type="password"/>
                                {errors.passwordConfirmation && touched.passwordConfirmation ? <div className="error">{errors.passwordConfirmation}</div> : null}
                            </div>

                            <div className="agreement">
                                By clicking Sign up, you agree to our Terms and Data Policy, including our Cookie Use.
                            </div>
                            <button type="submit">Sign up</button>
                        </Form>
                    )}
                </Formik>
            </div>
        )
    }
}

export default SignUpForm;