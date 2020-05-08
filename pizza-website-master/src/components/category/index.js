import React from 'react';
import './category.sass';
import PropTypes from 'prop-types';

class Category extends React.Component {

    render() {
        let buttons = [], { setCategory, category, currentCategory } = this.props;
        
        for (let [key, value] of Object.entries(category)) {
            buttons.push(<button key={key} disabled={currentCategory === value} onClick={() => setCategory(value)}>
                {value === "SIDE_DISH" ? "SIDE DISH" : value}
            </button>)
        }
        
        return (
            <div className="category-container">
                {buttons}
            </div>
        )
    }
}

Category.propTypes = {
    category: PropTypes.shape({
        all: PropTypes.string,
        pizza: PropTypes.string,
        size_dish: PropTypes.string,
        drink: PropTypes.string,
        dessert: PropTypes.string,
    }),
    setCategory: PropTypes.func,
    currentCategory: PropTypes.string
}

export default (Category);