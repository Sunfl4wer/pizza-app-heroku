import React from 'react';
import './box.sass';
import PropTypes from 'prop-types';
import { resetMessage } from '../../feature/message/messageSlice';
import { connect } from 'react-redux';

class message_box extends React.Component {
    constructor(props) {
        super(props);
        this.box = React.createRef();
        this.close = React.createRef();
    }

    componentDidMount() {
        this.close.current.addEventListener('click', () => {
            this.box.current.classList.toggle('active');
            this.props.dispatch(resetMessage());
        })

        window.onclick = (event) => {
            if (event.target === this.box.current) {
                this.box.current.style.display = "none";
                this.props.dispatch(resetMessage());
            }
        }
    }

    render () {
        let { message } = this.props;
        
        return (
            <div ref={this.box} className="messageBox">
                <div className="content">
                    <span ref={this.close} className="close">&times;</span>
                    <p>{message}</p>
                    <p className="caption" >(Click anywhere to dismiss)</p>
                </div>
            </div>
        )
    }
}

message_box.propTypes = {
    message: PropTypes.string
}

const mapState = (state) => {
    return {
        message: state.message
    }
}

export default connect(mapState)(message_box)