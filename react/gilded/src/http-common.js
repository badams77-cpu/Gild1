import axios from "axios"


export default axios.create({
//    baseURL: "http://localhost:8080",
    baseURL: "http://192.168.56.111:8080",
    headers: {
        "content-type": "application/json"
    }
});