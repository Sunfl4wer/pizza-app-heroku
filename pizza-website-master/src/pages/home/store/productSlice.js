import { createSlice } from '@reduxjs/toolkit';

export const category = {
    all: "ALL",
    pizza: "PIZZA",
    side_dish: "SIDE_DISH",
    drink: "DRINK",
    dessert: "DESSERT",
}

const productSlice = createSlice({
    name: 'productList',
    initialState: {
        meta: {
            "pagination": {
                "size": 10,
                "currentPage": 1,
                "offset": 10,
                "previousOffset": 0,
                "nextOffset": 10,
                "pageCount": 3,
                "totalCount": 23
            },
            "sortedBy": null
        },
        currentCategory: category.all,
        currentPage: 1,
        size: 10,
        pageCount: 1,
        loading: false,
        data: [],
        code: undefined
    } ,
    reducers: {
        fetch_request: (state, action) => {
            return {
                ...state,
                loading: true
            }
        },

        fetch_success: (state, action) => {
            return {
                ...state,
                pageCount: action.payload.meta.pagination.pageCount,
                currentPage: action.payload.meta.pagination.currentPage,
                data: action.payload.data,
                loading: false
            }
        },

        fetch_failure: (state, action) => {
            return {
                ...state,
                loading: false,
            }
        },

        setCategory: (state, action) => {
            return {
                ...state,
                currentCategory: action.payload
            }
        },

        setPage: (state, action) => {
            return {
                ...state,
                currentPage: action.payload,
            }
        },

        setSize: (state, action) => {
            let { id, sizeID } = action.payload;
            
            
            state.data.forEach( (product) => {
                if (product.id === id) {
                    product.pricing.selected = parseInt(sizeID);
                }
            })
        },

        setOption: (state, action) => {
            let { id, optionID } = action.payload;

            state.data.forEach( product => {
                if (product.id === id) {
                    product.additionalOption.selected = parseInt(optionID);
                }
            })
        }
    }
})

export const { fetch_failure, fetch_request, fetch_success, setCategory, setPage, setSize, setOption } = productSlice.actions;

export default productSlice.reducer