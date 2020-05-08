import { createSlice } from '@reduxjs/toolkit';

const messageSlice = createSlice({
    name: "message",
    initialState: window.sessionStorage.pizza_message ? window.sessionStorage.pizza_message : '',
    reducers: {
        setMessage: (state, action) => {
            window.sessionStorage.pizza_message = action.payload;
            return action.payload
        },

        resetMessage: (state, action) => {
            window.sessionStorage.removeItem("pizza_message");
            return ''
        }

    }
})

export const { setMessage, resetMessage } = messageSlice.actions;

export default messageSlice.reducer;