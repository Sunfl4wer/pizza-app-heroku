import { fetch_error, fetch_request, fetch_success } from '../store/userSlice';
import axios from 'axios';

export default (phoneNumber, passWord) => {
    return function(dispatch) {
        dispatch(fetch_request());
        axios.post('http://localhost/pizza/auth/signin', {
            "username": phoneNumber,
            "password": passWord
        })
            .then(res => dispatch(fetch_success(res.data)))
                .catch(error => dispatch(fetch_error(error.response.status)))
    }
}