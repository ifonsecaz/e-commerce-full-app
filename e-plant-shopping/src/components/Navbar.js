import { useState, useEffect } from 'react';
import './ProductList.css'
import { useSelector } from "react-redux";
import { Link, useNavigate} from 'react-router-dom';
import {postData} from './callBack';

function Navbar() {
    const cart = useSelector(state => state.cart.items);
    const [loggedIn,setLoggedIn]=useState(<div></div>);
    const navigate = useNavigate();

    const styleObj = {
        backgroundColor: '#4CAF50',
        color: '#fff!important',
        padding: '15px',
        display: 'flex',
        justifyContent: 'space-between',
        alignIems: 'center',
        fontSize: '20px',
    }
    const styleA = {
        color: 'white',
        fontSize: '30px',
        textDecoration: 'none',
    }

      const quantityInCart = () => {
        let num= 0;
        for(const items of cart){
            num+=items.quantity;
        }
        return num;
      }

      const logout = async (e) => {
        sessionStorage.setItem('username',"");
        postData('/auth/logout')
        navigate('/view-products');
      }


      useEffect(()=>{
             let curr_user = sessionStorage.getItem('username');

            if ( curr_user !== null &&  curr_user !== "") {
                    setLoggedIn(
                        <div style={{ display: 'flex', alignItems: 'center', width: '100%' }}>
                            <div style={{ flex: 1, display: 'flex', justifyContent: 'center' }}>
                                <Link to="/shopping" style={styleA}>Plants</Link>
                            </div>
                            
                            <div style={{ display: 'flex', alignItems: 'center', gap: '20px' }}>
                                <div style={{ flex: 1, display: 'flex', justifyContent: 'center' }}>
                                <Link to="/orders" style={styleA}>Orders</Link>
                            </div>
                                <div>
                                    <button
                                        onClick={logout}
                                        style={{
                                            backgroundColor: '#fff',
                                            color: '#4CAF50',
                                            border: 'none',
                                            borderRadius: '4px',
                                            padding: '10px 20px',
                                            fontSize: '16px',
                                            cursor: 'pointer',
                                            fontWeight: 'bold',
                                        }}
                                    >
                                        Logout
                                    </button>
                                </div>
                                <div>
                                    <Link to="/shopping-cart" style={styleA}>
                                        <h1 className='cart' style={{ position: 'relative', margin: 0 }}>
                                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 256 256" id="IconChangeColor" height="68" width="68">
                                                <rect width="156" height="156" fill="none"></rect>
                                                <circle cx="80" cy="216" r="12"></circle>
                                                <circle cx="184" cy="216" r="12"></circle>
                                                <path d="M42.3,72H221.7l-26.4,92.4A15.9,15.9,0,0,1,179.9,176H84.1a15.9,15.9,0,0,1-15.4-11.6L32.5,37.8A8,8,0,0,0,24.8,32H8" fill="none" stroke="#faf9f9" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" id="mainIconPathAttribute"></path>
                                            </svg>
                                            <span style={{
                                                position: 'absolute',
                                                top: '10px',
                                                right: '10px',
                                                backgroundColor: 'red',
                                                color: 'white',
                                                borderRadius: '50%',
                                                padding: '5px 10px',
                                                fontSize: '14px',
                                                fontWeight: 'bold'
                                            }}>
                                                {quantityInCart()}
                                            </span>
                                        </h1>
                                    </Link>
                                </div>
                            </div>
                        </div>
                    )
            }
            else{
                setLoggedIn(<div> 
                <div style={{ flex: 1, display: 'flex', justifyContent: 'center' }}>
                                <Link to="/view-products" style={styleA}>Plants</Link>
                </div>
                <Link  to="/register" className="nav_item">Register</Link >
                <Link  to="/login" className="nav_item">Login</Link >
                </div>)
            }
          }, [sessionStorage.getItem('username'),cart]);
      

      //plants click change logic wether is login or not, same for display cart or login
    return (
        <div>
        <div className="navbar" style={styleObj}>
        <div className="tag">
            <div className="luxury">
                <img src="https://cdn.pixabay.com/photo/2020/08/05/13/12/eco-5465432_1280.png" alt="" />
                <Link  to="/">
                    <div>
                        <h3 style={{ color: 'white' }}>Paradise Nursery</h3>
                        <i style={{ color: 'white' }}>Where Green Meets Serenity</i>
                    </div>
                </Link >
            </div>

        </div>
        {loggedIn}
        
    </div>
    </div>
    );
}
export default Navbar;