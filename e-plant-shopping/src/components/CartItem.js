import React,{useState} from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { removeItem, updateQuantity, resetCart } from './CartSlice';
import './CartItem.css';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { putData,deleteData } from './callBack';


const ErrorMessage = styled.div`
  margin-top: 0.5rem;
  color:rgb(184, 7, 7);
  font-weight: 600;
`;
const CartItem = () => {
  const cart = useSelector(state => state.cart.items);
  const dispatch = useDispatch();
    const navigate = useNavigate();
const validStatuses = [200, 201, 202, 204];
const [error, setError] = useState(null);

  // Calculate total amount for all products in the cart
  const calculateTotalAmount = () => {
        let total=0;
        for(const items of cart){
            total+=items.quantity*parseFloat(items.cost);
        }
        return total;
  };

  const handleContinueShopping = (e) => {
    navigate('/shopping'); 
  };

  const handleIncrement = async (item) => {
    console.log(item);
        let response = await putData('/user/increase-unit/product/'+item.productId);
            if(validStatuses.includes(response.status)){
                dispatch(updateQuantity({ name: item.name, quantity: item.quantity + 1 }));  
            }
            else{
                setError("Something went wrong, Try again later");
                setTimeout(() => setError(null), 5000);
            }
  };

  const handleDecrement = async(item) => {
    if(item.quantity===1){
      return;
    } 
    let response = await putData('/user/reduce-unit/product/'+item.productId);
    if(validStatuses.includes(response.status)){
         dispatch(updateQuantity({ name: item.name, quantity: item.quantity - 1 }));
    }
    else{
        setError("Something went wrong, Try again later");
        setTimeout(() => setError(null), 5000);
    }
     };

  const handleRemove = async (item) => {
    let response = await deleteData('/user/remove-from-cart/product/'+item.productId);
    if(validStatuses.includes(response.status)){
         dispatch(removeItem(item));
    }
    else{
        setError("Something went wrong, Try again later");
        setTimeout(() => setError(null), 5000);
    }
    
  };

  // Calculate total cost based on quantity for an item
  const calculateTotalCost = (item) => {
    let total=0;
    total=parseFloat(item.cost)*item.quantity;
    return total;
  };

    const handleCheckoutShopping = async (e) => {
        let response = await putData('/user/confirm-order');
        if(validStatuses.includes(response.status)){
            dispatch(resetCart());
        }
        else{
            setError("Something went wrong, Try again later");
            setTimeout(() => setError(null), 5000);
        }
    };

  return (
    <div className="cart-container">
      <h2 style={{ color: 'black' }}>Total Cart Amount: ${calculateTotalAmount()}</h2>
      <div>
        {error && <ErrorMessage>{error}</ErrorMessage>}
        {cart.map(item => (
          <div className="cart-item" key={item.name}>
            <img className="cart-item-image" src={item.image} alt={item.name} />
            <div className="cart-item-details">
              <div className="cart-item-name">{item.name}</div>
              <div className="cart-item-cost">${item.cost}</div>
              <div className="cart-item-quantity">
                <button className="cart-item-button cart-item-button-dec" onClick={() => handleDecrement(item)}>-</button>
                <span className="cart-item-quantity-value">{item.quantity}</span>
                <button className="cart-item-button cart-item-button-inc" onClick={() => handleIncrement(item)}>+</button>
              </div>
              <div className="cart-item-total">Total: ${calculateTotalCost(item)}</div>
              <button className="cart-item-delete" onClick={() => handleRemove(item)}>Delete</button>
            </div>
          </div>
        ))}
      </div>
      <div style={{ marginTop: '20px', color: 'black' }} className='total_cart_amount'></div>
      <div className="continue_shopping_btn">
        <button className="get-started-button" onClick={(e) => handleContinueShopping(e)}>Continue Shopping</button>
        <br />
        <button className="get-started-button1" onClick={handleCheckoutShopping}>Checkout</button>
      </div>
    </div>
  );
};

export default CartItem;


