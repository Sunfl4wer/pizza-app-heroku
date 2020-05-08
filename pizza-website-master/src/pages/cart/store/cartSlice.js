import { createSlice } from '@reduxjs/toolkit';

let index = 0;

const cartSlice = createSlice({
    name: 'cart',
    initialState: window.sessionStorage.cart ? JSON.parse(window.sessionStorage.cart) : [],
    reducers: {
        addToCart: (state, action) => {
            let product = action.payload, newState;

            if (state.length === 0) {
                newState = [ ...state ]
            } else {
                // check if product already exsists
                for (let i = 0; i < state.length; i++) {
                    if (state[i].id === product.id) {
                        // check if this is not a pizza then just add quantity
                        if (product.category !== "PIZZA") {
                            state[i].quantity++;
                            return;
                        } else {
                            // check if size & option are the similar
                            if (state[i].pricing.selected === product.pricing.selected && state[i].additionalOption.selected === product.additionalOption.selected) {
                                state[i].quantity++;
                                return;
                            }
                        }
                    }
                }

                newState = [ ...state ]
            }

            return [
                ...newState,
                {
                    index: ++index,
                    ...product,
                    quantity: 1,
                }
            ]
        },

        increaseQuantity: (state, action) => {
            let index = action.payload;

            state.forEach( item => {
                if (item.index === index) {
                    item.quantity++;
                }
            })
        },

        decreaseQuantity: (state, action) => {
            let index = action.payload;

            state.forEach( item => {
                if (item.index === index) {
                    item.quantity--;
                }
            })
        },

        setQuantity: (state, action) => {
            let { index, quantity } = action.payload;

            state.forEach( item => {
                if (item.index === index) {
                    item.quantity = quantity;
                }
            })
        },

        removeFromCart: (state, action) => {
            return state.filter( (value) => {
                return value.index !== action.payload
            })
        },

        save: (state, action) => {
            window.sessionStorage.cart = JSON.stringify(state);
        },

        reset: () => {
            window.sessionStorage.removeItem("cart");
            return []
        }
    }
})

export const { addToCart, removeFromCart, increaseQuantity, decreaseQuantity, setQuantity, save, reset } = cartSlice.actions;

export default cartSlice.reducer;