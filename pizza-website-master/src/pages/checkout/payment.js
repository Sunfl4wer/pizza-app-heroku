import React from 'react';
import PropTypes from 'prop-types';
import { Form, Formik, Field } from 'formik';
import * as Yup from 'yup';

const billingSchema = Yup.object().shape({
    name: Yup.string()
        .required('Required field.'),
    email: Yup.string()
        .email('Invalid email.')
        .required('Required field.'),
    phoneNumber: Yup.number()
        .typeError('Must be number only.')
        .required('Required field.'),
    address: Yup.string()
        .required('Required field'),
    method: Yup.string()
        .required('Choose 1 method.')
})

class Payment extends React.Component {
    constructor(props) {
        super(props);
        this.input = React.createRef();
    }

    confirmation = () => {
        let popup = document.querySelector(".modal");
        popup.style.display = "block"
    }

    render() {
        let { user } = this.props;

        return (
            <Formik initialValues={ user !== undefined ? 
                        {
                            name : `${user.firstName} ${user.lastName}`,
                            email : user.email,
                            phoneNumber : user.username,
                            address : user.address,
                            method: "cod",
                            cardNumber: '',
                            nameOnCard: '',
                            expire: '',
                            security: ''
                        } : 
                        {
                            name : '',
                            email : '',
                            phoneNumber : '',
                            address : '',
                            method: "cod",
                            cardNumber: '',
                            nameOnCard: '',
                            expire: '',
                            security: ''
                        }
                    }
                    validationSchema = {billingSchema}
                    validate={ (values) => {
                        const errors={};

                        if (values.method !== "cod") {
                            if (!values.nameOnCard) {
                                errors.nameOnCard = 'Required field'
                            }

                            if (!values.cardNumber) {
                                errors.cardNumber = 'Required field'
                            }

                            if (!values.expire) {
                                errors.expire = 'Required field'
                            }

                            if (!values.security) {
                                errors.security = 'Required field'
                            }
                        }

                        return errors;
                    }}
                    onSubmit={( values, { setSubmitting } ) => {
                        setSubmitting(true);
                        this.confirmation();
                    }} 
            >
                { ({ values, errors, touched }) => (
                    <Form className="info">
                        <div className="title">
                            Billing details
                        </div>

                        <div className="section">
                            <label>Name</label>
                            <Field type="text" name="name"/>
                            {errors.name && touched.name ? <div className="error">{errors.name}</div> : null}

                            <label>Email</label>
                            <Field type="text" name="email"/>
                            {errors.email && touched.email ? <div className="error">{errors.email}</div> : null}

                            <label>Phone number</label>
                            <Field type="number" name="phoneNumber"/>
                            {errors.phoneNumber && touched.phoneNumber ? <div className="error">{errors.phoneNumber}</div> : null}
                            
                            <label>Ship to</label>
                            <Field type="text" name="address"/>
                            {errors.address && touched.address ? <div className="error">{errors.address}</div> : null}
                            
                        </div>

                        <div className="title">
                            Payment method
                        </div>
                        
                        <div className="section">
                            <div className="cod">
                                <div className="methodTitle">
                                    <Field name="method" value="cod" type="radio"/>
                                    <span>COD (default)</span>
                                </div>
                            </div>

                            <div className="creditCard">
                                <div className="methodTitle">
                                    <Field name="method" value="creditCard" type="radio" />
                                    <span>Credit card</span>
                                </div>
                                <hr/>
                                <Field type="number" name="cardNumber" placeholder="Card number"/>
                                {errors.cardNumber && touched.cardNumber ? <div className="error">{errors.cardNumber}</div> : null}

                                <Field type="text" name="nameOnCard" placeholder="Name on card" />
                                {errors.nameOnCard && touched.nameOnCard ? <div className="error">{errors.nameOnCard}</div> : null}

                                <div className="inline" >
                                    <div className="block">
                                        <Field type="month" name="expire" placeholder="Expiration date"/>
                                        {errors.expire && touched.expire ? <div className="error">{errors.expire}</div> : null}
                                    </div>

                                    <div className="block">
                                        <Field type="number" name="security" placeholder="Security Code" />
                                        {errors.security && touched.security ? <div className="error">{errors.security}</div> : null}
                                    </div>
                                </div>
                                <div className="inline">
                                </div>
                            </div>
                        </div>

                        <button type="submit" >Place order</button>
                    </Form>
                )}
                
            </Formik>
        )
    }
}

Payment.propTypes = {
    empty: PropTypes.bool,
    cart: PropTypes.arrayOf(PropTypes.shape({
        id: PropTypes.string,
        name: PropTypes.string,

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

        category: PropTypes.string,
        quantity: PropTypes.number
    })),

    user: PropTypes.shape({
        firstName: PropTypes.string,
        lastName: PropTypes.string,
        email: PropTypes.string,
        address: PropTypes.string
    })
}

export default Payment;