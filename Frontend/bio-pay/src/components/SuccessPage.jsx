import React from "react";
import { useLocation } from "react-router-dom";
import "./SuccessPage.css";

export default function SuccessPage() {
  const { state } = useLocation();

  if (!state) {
    return <h1>No data available</h1>;
  }

  function generateTenDigitRandomNumber() {
    const min = 1000000000;
    const max = 9999999999;
    // Generate a random number between min (inclusive) and max (inclusive)
    return Math.floor(Math.random() * (max - min + 1)) + min;
  }

  const { fullName, email, initialBalance } = state;

  return (
    <div className="success-page">
      <div className="success-card">
        <div className="checkmark-container">
          <div className="checkmark">✓</div>
        </div>
        <h1 className="success-title">Registration Successful!</h1>
        <p className="success-subtitle">Welcome, <strong>{fullName}</strong> 🎉</p>

        <div className="details-box">
          <p><span>Email:</span> {email}</p>
          <p><span>Initial Balance:</span> ₹{initialBalance}</p>
        </div>

        <h4 className="home-btn" >
          Check Your Email For Further Details
        </h4>
        <h4>Acknowledgement Number: {generateTenDigitRandomNumber()} </h4>
      </div>
    </div>
  );
}
