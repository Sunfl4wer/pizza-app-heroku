import userReducer from './pages/sign-in/store/userSlice';
import productReducer from './pages/home/store/productSlice';
import productDetailReducer from './pages/detail/store/detailSlice';
import cartReducer from './pages/cart/store/cartSlice';
import previousPageReducer from './feature/previousPage/previousPageSlice';
import signUpReducer from './pages/sign-up/store/signUpSlice';
import messageReducer from './feature/message/messageSlice';
import { configureStore } from '@reduxjs/toolkit';

export default configureStore({
    reducer: {
      productList: productReducer,
      user: userReducer,
      productDetail: productDetailReducer,
      cart: cartReducer,
      previousPage: previousPageReducer,
      signUp: signUpReducer,
      message: messageReducer
    }
})