import { createSlice } from '@reduxjs/toolkit';

const historySlice = createSlice({
    name: "previousPage",
    initialState: '',
    reducers: {
        setPreviousPage: (state, action) => {
            return action.payload
        },

        resetPreviousPage: (state, action) => {
            return ''
        }

    }
})

export const { setPreviousPage, resetPreviousPage } = historySlice.actions;

export default historySlice.reducer;