import axios from 'axios';
const Genre_API_BASE_URL = `${process.env.REACT_APP_API_URL}api/genre`;
const Genre_API_BASE_URL2 = `${process.env.REACT_APP_API_URL}admin/genre`;
class GenreServices {

    getGenre() {
        return axios.get(Genre_API_BASE_URL);
    }
    deleteGenre(genreId, token) {
        const config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }
        console.log(config)
        return axios.delete(Genre_API_BASE_URL2 + '/' + genreId, config)
    }

}

export default new GenreServices();