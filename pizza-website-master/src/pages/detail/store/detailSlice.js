import { createSlice } from '@reduxjs/toolkit';

export const detailSlice = createSlice({
    name: "detail",
    initialState: {
        loading: false,
        product: undefined,
        code: undefined
    },
    reducers: {
        fetch_request: (state, action) => {
            return {
                ...state, loading: true
            }
        },

        fetch_success: (state, action) => {
            return {
                ...state,
                loading: false,
                product: {...action.payload.data},
                code: 200
            }
        },

        fetch_failure: (state, action) => {
            return {
                ...state,
                loading: false,
                error: action.payload.error
            }
        },

        setSize: (state, action) => {
            state.product.pricing.selected = parseInt(action.payload);
        },

        setOption: (state, action) => {
            state.product.additionalOption.selected = parseInt(action.payload);
        }
    }
})

export const { fetch_failure, fetch_request, fetch_success, setOption, setSize } = detailSlice.actions

export default detailSlice.reducer;