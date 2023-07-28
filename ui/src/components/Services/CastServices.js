import axios from 'axios';
const Cast_API_BASE_URL = `${process.env.REACT_APP_API_URL}api/cast`;
const Cast_API_BASE_URL2 = `${process.env.REACT_APP_API_URL}admin/cast`;
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