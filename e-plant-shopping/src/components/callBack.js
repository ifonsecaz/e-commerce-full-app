import axios from 'axios';

const apiInstance = axios.create({
    baseURL: `/api`,
    withCredentials: true
  });


export const fetchData = async (uri) => {
    try{
    const response = await apiInstance.get(uri);
    console.log(response);
    return response;
    }
    catch(error){
        if (axios.isAxiosError(error)) {
        console.error("Axios error:", error.response?.status, error.response?.data);
        return {
            status: error.response?.status || 500,
            data: error.response?.data || { message: "Unknown error" },
            error: true
        };
        } else {
        console.error("Unknown error:", error);
        return { status: 500, data: { message: "Unknown error" }, error: true };
        }
    }
};
    

export const postData = async (uri, body) => {
    try{
    const response = await apiInstance.post(uri, body, {
        headers: {
            'Content-Type': 'application/json',
        },
        withCredentials: true
        
    });
        console.log(response);

    return response;
    }
    catch(error){
        if (axios.isAxiosError(error)) {
        console.error("Axios error:", error.response?.status, error.response?.data);
        return {
            status: error.response?.status || 500,
            data: error.response?.data || { message: "Unknown error" },
            error: true
        };
        } else {
        console.error("Unknown error:", error);
        return { status: 500, data: { message: "Unknown error" }, error: true };
        }
    }
};


export const putData = async (uri, body) => {
    try{
    const response = await apiInstance.put(uri, body, {
        headers: {
            'Content-Type': 'application/json',
        },
        withCredentials: true
        
    });
        console.log(response);

    return response;
    }
    catch(error){
        if (axios.isAxiosError(error)) {
        console.error("Axios error:", error.response?.status, error.response?.data);
        return {
            status: error.response?.status || 500,
            data: error.response?.data || { message: "Unknown error" },
            error: true
        };
        } else {
        console.error("Unknown error:", error);
        return { status: 500, data: { message: "Unknown error" }, error: true };
        }
    }
};


export const deleteData = async (uri, body) => {
    try{
    const response = await apiInstance.delete(uri, body, {
        headers: {
            'Content-Type': 'application/json',
        },
        withCredentials: true
        
    });
        console.log(response);

    return response;
    }
    catch(error){
        if (axios.isAxiosError(error)) {
        console.error("Axios error:", error.response?.status, error.response?.data);
        return {
            status: error.response?.status || 500,
            data: error.response?.data || { message: "Unknown error" },
            error: true
        };
        } else {
        console.error("Unknown error:", error);
        return { status: 500, data: { message: "Unknown error" }, error: true };
        }
    }
};