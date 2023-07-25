import axios from "axios";
const API_URL = "https://8080-fbecdbbdadebcceabbaeaeaadbdbabf.project.examly.io/api/";
const API_URL2 = "https://8080-fbecdbbdadebcceabbaeaeaadbdbabf.project.examly.io/user/";
/*const register = (username,email,password)=>{
    return axios.post(API_URL+"register",{
        username,
        email,
        password
    })
}*/
const login = async (username, password) => {
    const res = await axios.post(API_URL + "login", {
        username,
        password
    })
        .then((res) => {
            if (res.data.token && res.status == 200) {
                localStorage.setItem("user", JSON.stringify(res.data))
            }
            console.log(res.data);
        })


}
const register = async (username, password, email) => {
    const res = await axios.post(API_URL + "register", {
        username,
        password,
        email
    })
    return res;
}


const logout = (token) => {
    const config = {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    }
    console.log(config)
    axios.get(API_URL2 + 'logout', config)
        .then((res) => {
            console.log('logged out')
        })
        .catch((err) => {

        })
    localStorage.removeItem("user");
}

const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("user"));
}
const AuthServices = {
    // register,
    login,
    logout,
    getCurrentUser,
    register
}

export default AuthServices;