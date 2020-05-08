import { createSlice } from "@reduxjs/toolkit";

const userSlice = createSlice({
    name: 'user',
    initialState: window.sessionStorage.user ? JSON.parse(window.sessionStorage.user) : {
        loading: false,
        data: undefined,
        code: undefined
    },
    reducers: {
        fetch_request: (state, action) => {
            return {
                ...state,
                loading: true
            }
        },

        fetch_success: (state, action) => {
            let { data, code } = action.payload;

            state.data = data;
            state.code = code;
            window.sessionStorage.user = JSON.stringify(state);
        },

        fetch_error: (state, action) => {
            return {
                ...state,
                code: action.payload
            }
        },

        sign_in: (state, action) => {
            return {
                ...state,
                data: action.payload,
                code: 200
            }
        },

        sign_out: (state, action) => {
            return {
                loading: false,
                data: undefined,
                code: undefined
            }
        },

        reset: () => {
            return {
                loading: false,
                data: undefined,
                code: undefined
            }
        },
    }
})

export const { fetch_error, fetch_request, fetch_success, sign_out, reset, sign_in } = userSlice.actions;

export default userSlice.reducer;