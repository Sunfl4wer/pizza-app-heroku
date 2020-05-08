import React from 'react';
import './dropdown.sass';
import PropTypes from 'prop-types';

class config_dropdown extends React.Component {
    constructor(props) {
        super(props);
        this.size = React.createRef();
        this.option = React.createRef();
    }

    changeSize = () => {
        this.props.sizeChanged(this.size.current.value);
    }

    changeOption = () => {
        this.props.optionChanged(this.option.current.value);
    }

    render() {
        let { size, option } = this.props;
        
        return (
            <div className="configuration">
                <label>Size:    
                    <select ref={this.size} value={size.selected} className="dropdown" onChange={() => this.changeSize()}>
                        {size.size.map((sizeTitle, index) => {
                            return <option className="option" key={index} value={index}>
                                {sizeTitle} { `(${`${new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(size.price[index])}`})` }</option> 
                    })}
                    </select>
                </label>

                <label>Add option:    
                    <select ref={this.option} value={option.selected} onChange={ () => this.changeOption() } className="dropdown">
                        {option.option.map((optionTitle, index) => {
                            return <option className="option" key={index} value={index} >
                                {optionTitle} { option.price[index] === 0 ? "" : `(${`${new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(option.price[index])}`})` }</option>
                        })}
                    </select>
                </label>
            </div>
        )
    }
}

config_dropdown.propTypes = {
    size: PropTypes.shape({
        size: PropTypes.arrayOf(PropTypes.string),
        price: PropTypes.arrayOf(PropTypes.number),
        selected: PropTypes.number
    }),
    option: PropTypes.shape({
        option: PropTypes.arrayOf(PropTypes.string),
        price: PropTypes.arrayOf(PropTypes.number),
        selected: PropTypes.number
    }),
}

export default config_dropdown;