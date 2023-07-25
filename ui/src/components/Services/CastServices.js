import axios from 'axios';
const Cast_API_BASE_URL = "http://localhost:8080/api/cast";
const Cast_API_BASE_URL2 = "http://localhost:8080/admin/cast";
class CastServices {

    deleteCast(castId, token) {
        const config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }
        console.log(config)
        return axios.delete(Cast_API_BASE_URL2 + '/' + castId, config);
    }

    getMovieByCast(castName) {
        return axios.get(Cast_API_BASE_URL + '/search?castName=' + castName)
    }

}

export default new CastServices();