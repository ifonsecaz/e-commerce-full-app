import React, { useState } from 'react';
import { Link, useNavigate} from 'react-router-dom';
import { fetchData, postData } from './callBack';
import styled from 'styled-components';
import "./Login.css";
import { useDispatch } from "react-redux";
import { addItemZero,resetCart} from "./CartSlice";

const ErrorMessage = styled.div`
  margin-top: 0.5rem;
  color:rgb(184, 7, 7);
  font-weight: 600;
`;

const Login = ({ onClose }) => {
  const navigate = useNavigate();
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
   const [error, setError] =useState(null);
   
    const dispatch = useDispatch();
  
   const login = async (e) => {
    e.preventDefault();
    let body= JSON.stringify({
            "username": userName,
            "password": password
    });
    const response = await postData('/auth/login', body);
    if(response.status===200){ //start a timer to generate new JWT
        sessionStorage.setItem('username', userName);
        dispatch(resetCart());
        if(loadCart()){
            navigate('/shopping');
        }
    }
    else {
        setError('Incorrect Username/Password');
        setTimeout(() => setError(null), 5000);
    }
};

 const loadCart = async()=>{
    const response = await fetchData('/user/view-cart');
    if(response.status===200){
        const response2=await fetchData('/user/view-order-details/'+response.data.order_id);
        if(response2.status===200){
            for(let i=0;i<response2.data.length;i++){
                let aux={
                    "productId":response2.data[i].product_id,
                    "name":response2.data[i].name,
                    "image":response2.data[i].image,
                    "cost":response2.data[i].unit_price,
                    "quantity":response2.data[i].quantity
                }
                dispatch(addItemZero(aux));
            }
        }
    }
    return true;
 }
  
  const cancel =()=>{
    navigate('/view-products');
  }

  return (
    <div>
    <div onClick={onClose}>
      <div
        onClick={(e) => {
          e.stopPropagation();
        }}
        className='modalContainer'
      >
          <form className="login_panel" style={{}} onSubmit={login}>
              <div>
              <span className="input_field">Username </span>
              <input type="text"  name="username" placeholder="Username" className="input_field" onChange={(e) => setUserName(e.target.value)}/>
              </div>
              <div>
              <span className="input_field">Password </span>
              <input name="psw" type="password"  placeholder="Password" className="input_field" onChange={(e) => setPassword(e.target.value)}/>            
              </div>
              <div>
              <input className="action_button" type="submit" value="Login"/>
              <input className="action_button" type="button" value="Cancel" onClick={()=>cancel()}/>
              </div>
              <Link to="/register" className="loginlink" >Register Now</Link>
          </form>
          {error && <ErrorMessage>{error}</ErrorMessage>}
      </div>
    </div>
    </div>
  );
};

export default Login;