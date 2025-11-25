import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./RegistrationForm.css";

export default function RegistrationForm() {
  const [fullName, setFullName] = useState("");
  const [email, setEmail] = useState("");
  const [pin, setPin] = useState("");
  const [cpin, setCPin] = useState("");
  const [phNumber, setphNumber] = useState("");
  const [initialBalance, setInitialBalance] = useState("");
  const [fingerprintImage, setFingerprintImage] = useState(null);
  const [statusMessage, setStatusMessage] = useState("");
  const [loading, setLoading] = useState(false); // <-- new state for loading

  const navigate = useNavigate();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    if (name === "fullName") setFullName(value);
    if (name === "email") setEmail(value);
    if (name === "pin") setPin(value);
    if(name==="cpin") setCPin(value);
    if(name==="phNumber") setphNumber(value);
    if (name === "initialBalance") setInitialBalance(value);

  };

  const handleFileUpload = (e) => {
    const file = e.target.files[0];
    if (file) setFingerprintImage(file);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if(!fullName){
      setStatusMessage("❗ Please fill fullName.");
      return;
    } 
    else if(!email){
      setStatusMessage("❗ Please fill email.");
      return;
    }
    else if(!pin){
      setStatusMessage("❗ Please fill pin.");
      return;
    } 
    else if(!cpin){
      setStatusMessage("❗ Please fill cpin.");
      return;
    }
    else if(!phNumber){
      setStatusMessage("❗ Please fill Phone Number.")
    }
    else if(!initialBalance){
      setStatusMessage("❗ Please fill initialBalance.");
      return;
    }
    else if(!fingerprintImage){
      setStatusMessage("❗ Finger Print Not Scanned");
      return;
    }

    if(pin.length!==5 || cpin.length!==5){
      setStatusMessage("! Invalid Pin");
    }

    const trimedPhNumber=phNumber.trim();
    if(trimedPhNumber.length!==10){
      setStatusMessage("! Invalid Phone Number");
    }

    if(pin!==cpin){
      setStatusMessage("❗ Pin Not Matching");
      return;
    }

    
    const trimedPhoneNumber=phNumber.trim();

    const formData = new FormData();
    formData.append("fullName", fullName);
    formData.append("email", email);
    formData.append("pin", pin);
    formData.append("phNumber",trimedPhoneNumber);
    formData.append("initialBalance", initialBalance);
    formData.append("fingerprintImage", fingerprintImage);

    try {
      setLoading(true);
      const response = await fetch(process.env.REACT_APP_REGISTER_URL, {
        method: "POST",
        body: formData,
      });
      console.log(response);
          //response.ok
      if (response.ok) {
        // Redirect to the success page and pass user data as state
        navigate("/success", {
          state: {
            fullName,
            email,
            initialBalance,
          },
        });
      } else {
        setStatusMessage("❌ Registration Failed. Please try again.");
      }
    } catch (error) {
      console.error("Error during registration:", error);
      setStatusMessage("⚠️ Server error. Please try again later.");
    }finally {
      setLoading(false); 
    }
  };

  return (
    <div className="registration-container">
      <div className="registration-form">
        <h2>Welcome to the Bio-Pay Bank</h2>
        <p className="subheading">Please fill in your details to register.</p>

        {statusMessage && <p className="status-message">{statusMessage}</p>}

        <form onSubmit={handleSubmit}>
          {/* Input fields and other form elements */}
          <div className="form-group">
            <label htmlFor="fullName">Full Name</label>
            <input
              type="text"
              id="fullName"
              name="fullName"
              value={fullName}
              onChange={handleInputChange}
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              name="email"
              value={email}
              onChange={handleInputChange}
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="cpin">PIN</label>
            <input
              type="password"
              id="pin"
              name="pin"
              value={pin}
              onChange={handleInputChange}
              required
              minLength="5"
              maxLength="5"
            />
          </div>

           <div className="form-group">
            <label htmlFor="pin">Confirm PIN</label>
            <input
              type="password"
              id="pin"
              name="cpin"
              value={cpin}
              onChange={handleInputChange}
              required
              minLength="5"
              maxLength="5"
            />
          </div>

          <div className="form-group">
            <label htmlFor="phNumber">Phone No.</label>
            <input
              type="number"
              id="phNumber"
              name="phNumber"
              value={phNumber}
              onChange={handleInputChange}
              required
              minLength="10"
              maxLength="10"
            />
          </div>

          <div className="form-group">
            <label htmlFor="initialBalance">Initial Balance</label>
            <input
              type="number"
              id="initialBalance"
              name="initialBalance"
              value={initialBalance}
              onChange={handleInputChange}
              required
              min="0"
              step="0.01"
            />
          </div>

          <div className="form-group">
            <label htmlFor="fingerprintImage" className="custom-file-label">
              {fingerprintImage ? "Fingerprint Scanned Successfully" : "Scan Fingerprint"}
            </label>
            <input
              type="file"
              id="fingerprintImage"
              name="fingerprintImage"
              accept="image/*"
              onChange={handleFileUpload}
              required
              className="file-input"
            />
            {fingerprintImage && (
              <img
                src={URL.createObjectURL(fingerprintImage)}
                alt="Fingerprint Preview"
                className="image-preview"
              />
            )}
          </div>

         
           <button
            type="submit"
            className="submit-btn"
            onClick={handleSubmit}
            disabled={loading} // <-- disable button while processing
          >
            {loading ? "Processing..." : "Register"} {/* <-- show animation text */}
          </button>

          {loading && (
            <div className="loading-spinner">
              {/* Simple spinner animation */}
              <div className="spinner"></div>
            </div>
          )}
        </form>
      </div>
    </div>
  );
}
