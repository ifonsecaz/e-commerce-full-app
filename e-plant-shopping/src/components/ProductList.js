import styled, { keyframes } from 'styled-components';


import React, { useState, useEffect } from 'react';
import './ProductList.css'
import { useSelector, useDispatch } from "react-redux";
import { addItem} from "./CartSlice";
import { fetchData, postData} from './callBack';

const spin = keyframes`
  0% { transform: rotate(0deg);}
  100% { transform: rotate(360deg);}
`;

const Spinner = styled.div`
  border: 4px solid rgba(0, 0, 0, 0.1);
  border-left-color: #0077cc;
  border-radius: 50%;
  width: 48px;
  height: 48px;
  animation: ${spin} 1s linear infinite;
  margin: 2rem auto;
`;

const ErrorMessage = styled.div`
  margin-top: 0.5rem;
  color:rgb(184, 7, 7);
  font-weight: 600;
`;

function ProductList() {
    const dispatch = useDispatch();
    const [loading, setLoading] = useState(true);
    const validStatuses = [200, 201, 202, 204];
    const [plantsArray, setPlantsArray] = useState([]);
    const [categoriesArray, setCategoriesArray] = useState([]);
    const [selectedCategory, setSelectedCategory] = useState(null);
    const [name,setName]=useState('');
    const [error, setError] = useState(null);
    

    const cart = useSelector(state => state.cart.items);

    //const [addedToCart, setAddedToCart] = useState({});
    const [prevState, setAddedToCart] = useState({});

    const populate =(response)=>{
        let Arrres=[];
              let auxArr=[];
              let aux={};
              let currCat=response.data[0].category;
              let currIdx=response.data[0].categoryId;
              for(let i=0;i<response.data.length;i++){
                  if(response.data[i].categoryId!==currIdx){
                      Arrres.push({"categoryId":currIdx,"category":currCat,"plants":auxArr})
                      auxArr=[];
                      currCat=response.data[i].category;
                      currIdx=response.data[i].categoryId;
                  }
                  auxArr.push({"productId":response.data[i].productId,"name":response.data[i].name,"image":response.data[i].image,"description":response.data[i].description,"cost":response.data[i].price,"stockQuantity":response.data[i].stockQuantity})
                  if(cart.find(item => item.name === response.data[i].name)){
                        aux[response.data[i].name] = true;
                    }
              }
              Arrres.push({"categoryId":currIdx,"category":currCat,"plants":auxArr})
            setPlantsArray(Arrres);
            setAddedToCart(aux);
    }

     const fetchAndDisplayData=async ()=>{
          let response=await fetchData('/products/list');
          if(validStatuses.includes(response.status)){
              populate(response);
  
               setLoading(false);
          }
      }
  
      const fetchByName=async (name)=>{
          let response=await fetchData('/products/name/'+name);
          if(validStatuses.includes(response.status)){
              populate(response);
  
               setLoading(false);
          }
      }
  
      const fetchCategories=async ()=>{
          let response=await fetchData('/products/category');
          if(validStatuses.includes(response.status)){
              setCategoriesArray(response.data);
          }
      }
  
      useEffect(()=>{
          fetchAndDisplayData();
          fetchCategories();
      }, [cart]);
  
  if (loading) {
          return (<><Spinner /> <p>Loading...</p></>);
      } 
  
  
      const handleCategoryClick = async (category) => {
          setLoading(true);
          setSelectedCategory(category.category);
          if(selectedCategory==="All Categories"){
              setSelectedCategory(null);
              return fetchAndDisplayData();
          }
  
          let response = await fetchData("/products/category/"+category.category);
          if (validStatuses.includes(response.status)) {
              populate(response);
  
               setLoading(false);
               const menu = document.getElementById('category-menu');
              if (menu) menu.style.display = 'none';
          }    
        
      };
      
      const handleSubmit = (e) => {
          e.preventDefault();
          if (name !== '') {
              fetchByName(name);
          } else {
              fetchAndDisplayData();
          }
      };
  
      const handleNameChange = (e) => {
          setName(e.target.value);
      };

    const handleAddToCart = async (product) => {
        let body= JSON.stringify({
            "product_id": product.productId,
            "quantity": 1
        });
        
        let response = await postData('/user/add-to-cart', body);
        if(validStatuses.includes(response.status)){
            dispatch(addItem(product));
            setAddedToCart((prevState) => ({
            ...prevState,
            [product.name]: true, // Set the product name as key and value as true to indicate it's added to cart
            }));
        }
        else{
            setError("Something went wrong, Try again later");
            setTimeout(() => setError(null), 5000);
        }
    
      };
/*
      useEffect(() => {
        setAddedToCart({});
        fetchAndDisplayData();

        for(const cat of plantsArray){
            for(const plant of cat.plants){
                if(cart.find(item => item.name === plant.name)){
                    setAddedToCart((prevState)=>({...prevState,[plant.name]:true,}));
                }
            }   
        }
      }, []);
*/

if (loading) {
        return (<><Spinner /> <p>Loading...</p></>);
    } 

    return (
        <div>
            <div className="dropdown" style={{ position: 'relative', zIndex: 100 }}>
                <button
                    style={{
                        background: '#20bd30', 
                        border: 'none',
                        cursor: 'pointer',
                        padding: '8px', 
                        fontSize: '24px', 
                        color: 'white',
                        borderRadius: '8px'
                    }}
                    onClick={() => {
                        const menu = document.getElementById('category-menu');
                        if (menu) menu.style.display = menu.style.display === 'block' ? 'none' : 'block';
                    }}
                    aria-label="Filter by category"
                >
                    &#9776;
                </button>
                <div
                    id="category-menu"
                    style={{
                        display: 'none',
                        position: 'fixed',
                        top: '60px', 
                        left: '20px',
                        background: '#222',
                        color: '#fff',
                        minWidth: '220px', 
                        boxShadow: '0px 8px 24px 0px rgba(0,0,0,0.3)',
                        zIndex: 9999, 
                        borderRadius: '8px',
                        overflow: 'hidden'
                    }}
                    onMouseLeave={() => {
                        const menu = document.getElementById('category-menu');
                        if (menu) menu.style.display = 'none';
                    }}
                >
                    <div>
                        <button
                            style={{
                                width: '100%',
                                padding: '14px',
                                background: selectedCategory === null ? '#444' : '#222',
                                color: '#fff',
                                border: 'none',
                                textAlign: 'left',
                                cursor: 'pointer',
                                fontSize: '18px'
                            }}
                            onClick={async () => {
                                setLoading(true);
                                setSelectedCategory(null);
                                await fetchAndDisplayData();
                                setLoading(false);
                                const menu = document.getElementById('category-menu');
                                if (menu) menu.style.display = 'none';
                            }}
                        >
                            All Categories
                        </button>
                    </div>
                    {categoriesArray.map((category) => (
                        <div key={category.categoryId}>
                            <button
                                style={{
                                    width: '100%',
                                    padding: '14px',
                                    background: selectedCategory === category.categoryId ? '#444' : '#222',
                                    color: '#fff',
                                    border: 'none',
                                    textAlign: 'left',
                                    cursor: 'pointer',
                                    fontSize: '18px'
                                }}
                                onClick={() => handleCategoryClick(category)}
                            >
                                {category.category}
                            </button>
                        </div>
                    ))}
                </div>
            </div>
            <div style={{ flex: 1, display: 'flex', justifyContent: 'center' }}>
                <div style={{ display: 'flex', alignItems: 'center', gap: '20px' }}>
                    

                    <form
                        onSubmit={handleSubmit}
                        style={{ display: 'flex', alignItems: 'center' }}
                    >
                        <input
                            type="text"
                            name="search"
                            placeholder="Search plants..."
                            value={name}
                            onChange={handleNameChange}
                            style={{
                                padding: '8px',
                                borderRadius: '4px 0 0 4px',
                                border: '1px solid #ccc',
                                fontSize: '16px'
                            }}
                        />
                        <button
                            type="submit"
                            style={{
                                padding: '8px 12px',
                                border: 'none',
                                borderRadius: '0 4px 4px 0',
                                background: '#388e3c',
                                color: 'white',
                                fontWeight: 'bold',
                                cursor: 'pointer'
                            }}
                        >
                            Search
                        </button>
                    </form>
                    
                </div>
            </div>
            {error && <ErrorMessage>{error}</ErrorMessage>}
            <div className="product-grid">
                {plantsArray.map((category) => (
                    <div key={category.categoryId}>
                        <h1><div>{category.category}</div></h1>
                        <div className="product-list">
                            {category.plants.map((plant) => (
                            <div className={`product-card ${plant.stockQuantity<=0 ? 'before-out':'before-av'}`} key={plant.productId}>
                                <img className="product-image" src={plant.image} alt={plant.name} />
                                <div className="product-title">{plant.name}</div>
                                <div className="product-price">${plant.cost}</div>
                                <div>{plant.description}</div>
                                <button className={`product-button${prevState[plant.name] || plant.stockQuantity <= 0 ? ' added-to-cart' : ''}`} onClick={() => handleAddToCart(plant)} disabled={prevState[plant.name] || plant.stockQuantity <= 0}>Add to Cart</button>
                            </div>
                            ))}
                        </div>
                    </div>
                ))}

            </div>

        </div>
    );
}

export default ProductList;
