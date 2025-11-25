import React, { useState, useEffect } from "react";
import "./PaymentPad.css";
import fingerprintImg from "../assets/fingerprint.png";

export default function PaymentPad() {
  const [pin, setPin] = useState("");
  const [amount, setAmount] = useState("");
  const [image, setImage] = useState(null);
  const [isEnteringPin, setIsEnteringPin] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [statusMessage, setStatusMessage] = useState("");

  // 🌍 Location states (hidden, used internally)
  const [location, setLocation] = useState({ lat: null, lon: null, address: "" });
  const [locationReady, setLocationReady] = useState(false);

  const API_KEY = "1f5d14fefacf48bd95e2fc12d64dee6a"; // OpenCage API key

  // 📍 Fetch user location (runs once)
  useEffect(() => {
    if (!navigator.geolocation) {
      console.warn("Geolocation not supported by this Machine.");
      return;
    }

    navigator.geolocation.getCurrentPosition(
      async (position) => {
        const { latitude, longitude } = position.coords;
        let addressText = "";
        try {
          const response = await fetch(
            `https://api.opencagedata.com/geocode/v1/json?q=${latitude}+${longitude}&key=${API_KEY}`
          );
          const data = await response.json();
          if (data.results && data.results.length > 0) {
            addressText = data.results[0].formatted;
          }
        } catch (err) {
          console.warn("Failed to fetch address from OpenCage.");
        }

        setLocation({ lat: latitude, lon: longitude, address: addressText });
        setLocationReady(true);
      },
      (err) => {
        console.warn("Location access denied or unavailable:", err.message);
        setLocationReady(false);
      }
    );
  }, []);

  // 🔢 Keypad input
  const handleNumberClick = (num) => {
    if (isEnteringPin) {
      if (pin.length < 5) setPin(pin + num);
    } else {
      if (amount.length < 6) setAmount(amount + num);
    }
  };

  const handleUpload = (e) => {
    const file = e.target.files[0];
    if (file) setImage(file);
  };

  const handleClear = () => {
    if (isEnteringPin) setPin("");
    else setAmount("");
    if (image) setImage(null);
  };

  const handleModeSwitch = () => setIsEnteringPin(!isEnteringPin);

  // 💳 Submit payment
  const handleSubmit = async () => {
    if (!amount || !pin || !image) {
      alert("Please enter amount, PIN, and upload fingerprint image.");
      return;
    }

    if (!locationReady || !location.lat || !location.lon) {
      alert("Location not detected. Please allow location access.");
      return;
    }

    console.log(location.address);

    setIsSubmitting(true);
    setStatusMessage("⏳ Processing...");

    try {
      const formData = new FormData();
      formData.append("amount", amount);
      formData.append("pin", pin);
      formData.append("fingerprint", image);
      // formData.append("latitude", location.lat);
      // formData.append("longitude", location.lon);
      formData.append("location", location.address);

      // 🔗 Your backend endpoint
      const response = await fetch(process.env.REACT_APP_PAY_URL, {
        method: "POST",
        body: formData,
      });

      if (response.status===200) {
        setStatusMessage("✅ Payment Successful!");
        setAmount("");
        setPin("");
        setImage(null);
        setIsEnteringPin(false);
      } else {
        setStatusMessage("❌ Transaction Failed!");
        setAmount("");
        setPin("");
        setImage(null);
        setIsEnteringPin(false);
      }
    } catch (error) {
      console.error("Error submitting payment:", error);
      setStatusMessage("⚠️ Server Error. Please try again.");
      setAmount("");
      setPin("");
      setImage(null);
      setIsEnteringPin(false);
    }

    setIsSubmitting(false);
    setTimeout(() => setStatusMessage(""), 5000);
  };

  return (
    <div className="pad-container">
      <div className="pad-card">
        <div className="display">
          {statusMessage ? (
            <p className="status-message">{statusMessage}</p>
          ) : isEnteringPin ? (
            <>
              <p>TOTAL: {amount || 0}</p>
              <p>ENTER YOUR PIN</p>
              <div className="pin-display">{"*".repeat(pin.length)}</div>
            </>
          ) : (
            <>
              <p>ENTER AMOUNT</p>
              <div className="amount-display">{amount || 0}</div>
            </>
          )}
        </div>

        <div className="keypad">
          {[1, 2, 3, 4, 5, 6, 7, 8, 9, 0].map((num) => (
            <button
              key={num}
              onClick={() => handleNumberClick(num)}
              className="key-button"
            >
              {num}
            </button>
          ))}
        </div>

        <div className="finger-section">
          <img src={fingerprintImg} alt="Fingerprint" className="finger-img" />
        </div>

        <div className="upload-section">
          <label className="upload-label">
            {image ? "Fingerprint Scanned Successfully" : "Scan Fingerprint"}
            <input
              type="file"
              className="file-input"
              accept="image/*"
              onChange={handleUpload}
            />
            {image && (
              <img
                src={URL.createObjectURL(image)}
                alt="Preview"
                className="upload-preview"
              />
            )}
          </label>
        </div>

        <div className="actions">
          <button className="clear-btn" onClick={handleClear}>
            Clear
          </button>
          <button className="mode-btn" onClick={handleModeSwitch}>
            {isEnteringPin ? "Edit Amount" : "Enter PIN"}
          </button>
        </div>

        <button
          className="submit-btn"
          onClick={handleSubmit}
          disabled={isSubmitting}
        >
          {isSubmitting ? "Processing..." : "Pay"}
          
        </button>
      </div>
    </div>
  );
}
