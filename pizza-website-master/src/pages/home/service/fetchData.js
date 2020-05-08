import axios from 'axios';
import { fetch_success, fetch_request, fetch_failure, category } from '../store/productSlice';

export default (cat, page, size) => {
    let api;

    if (cat === category.all) {
        api = `http://localhost/pizza/productcards?size=${size}&page=${page}`;
    } else {
        api = `http://localhost/pizza/productcards?category=${cat}&size=${size}&page=${page}`;
    }

    return function(dispatch) {
        dispatch(fetch_request())
        axios.get(api)
            .then(res => dispatch(fetch_success(res.data)))
                .catch(error => dispatch(fetch_failure(error.response)))
    }
}