import { fetch_error, fetch_request, fetch_success } from '../store/signUpSlice';
import axios from 'axios';

export default (user) => {
    return function(dispatch) {
        dispatch(fetch_request());
        axios.post('http://localhost/pizza/auth/signup', {
            ...user,
            "roles": []
        })
            .then(res => dispatch(fetch_success(res.data.code)))
                .catch(error => dispatch(fetch_error(error.response.data.errors.code)))
    }
}