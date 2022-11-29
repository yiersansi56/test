import axios from "axios";
const http_1 = axios.create({
    baseURL:'http://localhost:8090',
    timeout: 8000,
});
export const getCovData = () =>http_1.get('/data')