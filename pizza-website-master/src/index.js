import React from 'react';
import ReactDOM from 'react-dom';
import './index.sass';
import * as serviceWorker from './serviceWorker';
import { BrowserRouter as Router, Switch, Route, Redirect } from 'react-router-dom';
import { Provider } from 'react-redux';
import store from './store';

// pages
import Home from './pages/home';
import SignIn from './pages/sign-in';
import Cart from './pages/cart';
import Error from './pages/error';
import Detail from './pages/detail';
import SignUp from './pages/sign-up';
import Checkout from './pages/checkout';

ReactDOM.render(
  <Router>
    <React.StrictMode>
      <Provider store={store} >
          <Switch>
            <Route exact path="/" component={Home} />
            <Route path="/detail/:id" component={Detail} />
            <Route path="/sign-in" component={SignIn} />
            <Route path="/sign-up" component={SignUp} />
            <Route path="/cart" component={Cart} />
            <Route path="/checkout" component={Checkout} />
            <Redirect from="/homepage" to="/" />
            <Redirect from="/trang-chu" to="/" />
            <Route component={Error}/>
          </Switch>
      </Provider>
    </React.StrictMode>
  </Router>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
