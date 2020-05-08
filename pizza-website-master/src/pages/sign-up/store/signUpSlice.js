import { createSlice } from '@reduxjs/toolkit';

const signUpSlice = createSlice({
    name: 'signUp',
    initialState: {
        loading: false,
        user: undefined,
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
            return {
                ...state,
                loading: false,
                code: action.payload
            }
        },

        fetch_error: (state, action) => {
            return {
                ...state,
                code: action.payload
            }
        },
        
        setUser: (state, action) => {
            return {
                ...state,
                user: action.payload
            }
        },

        reset: (state, action) => {
            return {
                ...state,
                code: undefined
            }
        }
    }
})

export const { fetch_error, fetch_request, fetch_success, reset, setUser } = signUpSlice.actions;

export default signUpSlice.reducer;