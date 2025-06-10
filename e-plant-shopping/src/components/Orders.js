import React, { useEffect, useState } from "react";
import styled, { keyframes } from 'styled-components';
import { deleteData, fetchData } from "./callBack";

const ErrorMessage = styled.div`
  margin-top: 0.5rem;
  color:rgb(184, 7, 7);
  font-weight: 600;
`;

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


const Orders = () => {
    const [orders, setOrders] = useState([]);
    const [expanded, setExpanded] = useState({});
    const [error, setError] = useState(null);
    const validStatuses = [200, 201, 202, 204];
    const [loading, setLoading] = useState(true);

    const fetchOrders = async () => {
        let response = await fetchData('user/view-orders');
        setLoading(true);
        if(validStatuses.includes(response.status)){
            let res=[];
            for(let i=0;i<response.data.length;i++){
                let resDet=[];
                let response2 = await fetchData('user/view-order-details/'+response.data[i].order_id);
                if(validStatuses.includes(response2.status)){
                    for(let j=0;j<response2.data.length;j++){
                        resDet.push({"order_detail_id": response2.data[j].order_detail_id,"name":response2.data[j].name,"image":response2.data[j].image,"unit_price":response2.data[j].unit_price,"quantity":response2.data[j].quantity});
                    }
                }
                res.push({"order_id":response.data[i].order_id,"order_date":response.data[i].order_date,"total_amount":response.data[i].total_amount,"order_status":response.data[i].order_status,"orderdetails":resDet});
            }
            console.log(res);
            setOrders(res);
        }
        else{
            setError("Something went wrong, Try again later");
            setTimeout(() => setError(null), 5000);
        }
        setLoading(false);
    };

    const cancelOrder = async (orderId) => {
        let response = await deleteData('user/cancel-order/'+orderId);
        if(validStatuses.includes(response.status)){
            return { success: true };
        }
        else{
            setError("Something went wrong, Try again later");
            setTimeout(() => setError(null), 5000);
        }
    };


    useEffect(() => {
        fetchOrders();
    }, []);

    const handleExpand = (orderId) => {
        setExpanded((prev) => ({
            ...prev,
            [orderId]: !prev[orderId],
        }));
    };

    const handleCancel = async (orderId) => {
        if (window.confirm("Are you sure you want to cancel this order?")) {
            const res = await cancelOrder(orderId);
            if (res.success) {
                setOrders((prev) =>
                    prev.map((order) =>
                        order.order_id === orderId
                            ? { ...order, order_status: "Cancelled" }
                            : order
                    )
                );
            }
        }
    };

    if (loading) {
        return (<><Spinner /> <p>Loading...</p></>);
    } 

    return (
        <div>
            <h2>Your Orders</h2>
            {error && <ErrorMessage>{error}</ErrorMessage>}
            {orders.length === 0 && <p>No orders found.</p>}
            {orders.map((order) => (
                <div
                    key={order.order_id}
                    style={{
                        border: "1px solid #ccc",
                        borderRadius: 8,
                        marginBottom: 16,
                        padding: 16,
                    }}
                >
                    <div
                        style={{ cursor: "pointer", display: "flex", justifyContent: "space-between" }}
                        onClick={() => handleExpand(order.order_id)}
                    >
                        <div>
                            <strong>Order ID:</strong> {order.order_id} <br />
                            <strong>Date:</strong> {order.order_date} <br />
                            <strong>Total:</strong> ${order.total_amount.toFixed(2)} <br />
                            <strong>Status:</strong> {order.order_status}
                        </div>
                        <div>
                            <button
                                onClick={(e) => {
                                    e.stopPropagation();
                                    handleExpand(order.order_id);
                                }}
                                style={{ marginRight: 8 }}
                            >
                                {expanded[order.order_id] ? "Hide Details" : "Show Details"}
                            </button>
                            {order.order_status !== "Cancelled" && order.order_status !== "Not completed" && (
                                <button
                                    onClick={(e) => {
                                        e.stopPropagation();
                                        handleCancel(order.order_id);
                                    }}
                                    style={{ background: "#e74c3c", color: "#fff" }}
                                >
                                    Cancel Order
                                </button>
                            )}
                        </div>
                    </div>
                    {expanded[order.order_id] && (
                        <div style={{ marginTop: 16 }}>
                            <h4>Order Details</h4>
                            <table width="100%" border="1" cellPadding="8" style={{ borderCollapse: "collapse" }}>
                                <thead>
                                    <tr>
                                        <th>Product</th>
                                        <th>Image</th>
                                        <th>Unit Price</th>
                                        <th>Quantity</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {order.orderdetails.map((detail) => (
                                        <tr key={detail.order_detail_id}>
                                            <td>{detail.name}</td>
                                            <td>
                                                <img
                                                    src={detail.image}
                                                    alt={detail.name}
                                                    style={{ width: 60, height: 60, objectFit: "cover" }}
                                                />
                                            </td>
                                            <td>${detail.unit_price.toFixed(2)}</td>
                                            <td>{detail.quantity}</td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    )}
                </div>
            ))}
        </div>
    );
};

export default Orders;