import React from "react";
import { Link } from "react-router-dom";
import "./Home.css";
import logo from "../assets/logo.png"; // Make sure this path matches your logo location

export default function Home() {
  return (
    <div className="home-container">
      <nav className="navbar">
        <img src={logo} alt="Bio Pay Bank Logo" className="navbar-logo" />
        <span className="navbar-title">Bio-Pay Bank</span>
      </nav>
      <div className="home-card">
        <h1>Welcome to Bio-Pay Bank</h1>
        <p>Your secure biometric and PIN-based banking solution.</p>
        <div className="home-links">
          <Link to="/register" className="home-button register-btn">
            Register
          </Link>
          <Link to="/machine" className="home-button machine-btn">
            Machine Interface(Virtual)
          </Link>
          <Link to="/addmoney" className="home-button machine-btn">
            Add Money
          </Link>
        </div>
      </div>
    </div>
  );
}
