import React from 'react';
import './pagination.sass';
import PropTypes from 'prop-types';

class Pagination extends React.Component {

    render() {
        let buttons = [], { setPage, currentPage, pageCount } = this.props;

        for (let i = 1; i <= pageCount; i++) {
            buttons.push(<button disabled={currentPage === i} onClick={() => setPage(i)} key={i} className="index" >
                {i}
            </button>)
        }

        return (
            <div className="pagination-container">
                <button disabled={currentPage === 1} onClick={() => setPage(currentPage - 1)} className="prev" >
                    <img src={ require('../../assets/images/prev.png') } alt="prev"/>
                </button>

                {buttons}

                <button disabled={currentPage === pageCount} onClick={() => setPage(currentPage + 1)} className="next" >
                    <img src={ require('../../assets/images/next.png') } alt="next"/>
                </button>
            </div>
        )
    }
}

Pagination.propTypes = {
    currentPage: PropTypes.number,
    setPage: PropTypes.func,
    pageCount: PropTypes.number
}

export default Pagination;