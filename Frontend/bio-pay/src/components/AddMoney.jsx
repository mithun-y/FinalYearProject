import React, { useEffect, useState } from "react";
import "./AddMoney.css";

const AddBalance = () => {
  const [phoneNumber, setPhoneNumber] = useState("");
  const [account, setAccount] = useState(null);
  const [message, setMessage] = useState("");
  const [money, setMoney] = useState(0);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false); 

  const handleChange = (e) =>{
    setPhoneNumber(e.target.value);

  }

  useEffect(() => {
    if (phoneNumber.trim().length === 10) handleFetchAccount();
    if(phoneNumber.trim().length<10) {
      setAccount(null);
    }
  }, [phoneNumber]);

  const handleFetchAccount = async () => {
    setMessage("");
    setError("");

    const trimmedPhone = phoneNumber.trim();

    try {
      const response = await fetch(
        `http://localhost:9091/api/update/check/${trimmedPhone}`
      );

      if (response.status === 200) {
        const data = await response.json();
        setAccount(data);
      } else if (response.status === 404) {
        setError("Account not found!");
      } else {
        setError("Something went wrong!");
      }
    } catch {
      setError("Server error!");
    }
  };

  const handleAddBalance = async () => {
    setMessage("");
    setError("");

    const trimmedPhone = phoneNumber.trim();
    setLoading(true);

    try {
      const response = await fetch(
        `http://localhost:9091/api/update/addbalance/${trimmedPhone}/${money}`,
        {
          method: "PATCH",
          headers: { "Content-Type": "application/json" },
        }
      );

      if (response.status === 200) {
        setMessage("Balance added successfully!");
        setAccount(null);
        setPhoneNumber("");
        setMoney(0);
      } else if (response.status === 404) {
        setError("Account not found!");
      } else {
        setError("Something went wrong!");
      }
    } catch {
      setError("Server error!");
    }finally {
      setLoading(false); 
    }
  };

  return (
    <div className="modern-container">
      <div className="glass-card">

        <h2 className="title">Add Balance</h2>

        <input
          type="text"
          value={phoneNumber}
          maxLength={10}
          onChange={handleChange}
          placeholder="Enter phone number"
          className="input-modern"
        />

        {account && (
          <div className="account-box fade-in">
            <p><strong>Name:</strong> {account.fullName}</p>
            <input
              type="number"
              value={money}
              onChange={(e) => setMoney(e.target.value)}
              placeholder="Enter amount"
              className="input-modern"
            />

             <button
              className="modern-btn"
              onClick={handleAddBalance}
              disabled={loading} // disable button while loading
            >
              {loading ? `Processing...` : `Pay ₹${money}`}
            </button>

            {loading && (
              <div className="loading-spinner">
                <div className="spinner"></div>
              </div>
            )}
          </div>
        )}

        {message && <p className="msg success">{message}</p>}
        {error && <p className="msg error">{error}</p>}



      </div>
    </div>
  );
};

export default AddBalance;
