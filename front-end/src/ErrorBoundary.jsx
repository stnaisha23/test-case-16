import React from 'react';
import axios from 'axios';

class ErrorBoundary extends React.Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false };
  }

  static getDerivedStateFromError() {
    return { hasError: true };
  }

  componentDidCatch(error, errorInfo) {
    this.sendErrorLog(error, errorInfo);
  }

  sendErrorLog = async (error, errorInfo) => {
    const errorData = {
      error: `${error.message}\n${error.stack}`,
      errorInfo: errorInfo.componentStack,
    };

    try {
      await axios.post('http://localhost:8091/sample/error', errorData);
    } catch (err) {
      console.error("Gagal mengirim log error", err);
    }
  };

  render() {
    if (this.state.hasError) {
      return <h1>Something went wrong.</h1>;
    }
    return this.props.children;
  }
}

export default ErrorBoundary;
