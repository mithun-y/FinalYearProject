import './App.css';
import { Routes, Route } from 'react-router-dom';
import Register from './components/RegistrationForm';
import PaymentPad from './components/PaymentPad';
import SuccessPage from './components/SuccessPage';
import Home from './components/Home';
import AddMoney from "./components/AddMoney";

function App() {
  return (
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/register" element={<Register />} />
        <Route path="/machine" element={<PaymentPad />} />
        <Route path="/success" element={<SuccessPage />} />
        <Route path="/addmoney" element={<AddMoney />}  />
      </Routes>
  );
}

export default App;
