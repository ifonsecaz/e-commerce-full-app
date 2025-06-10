
import React from 'react';
import ProductList from './components/ProductList'; //Crear uno sin botones
import CartItem from './components/CartItem';
import Navbar from './components/Navbar';
import Login from './components/Login';
import Register from './components/Register';
import ProductListNotLogged from './components/ProductListNotLogged';
import Orders from './components/Orders';
//import './App.css';
import AboutUs from './components/AboutUs';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; 

function App() {

  return (
    <div className="app-container">
        <Router>
        <Navbar/>
        <Routes>
          <Route path="/" element={<AboutUs/>}/>
          <Route path="/view-products" element={<ProductListNotLogged/>}/> 
          <Route path="/shopping" element={<ProductList/>}/>
          <Route path="/shopping-cart" element={<CartItem/>}/>
          <Route path="/login" element={<Login/>}/>
          <Route path="/register" element={<Register/>}/>
          <Route path="/orders" element={<Orders/>}/>
        </Routes>
        </Router>     
    </div>
  );
}

export default App;



