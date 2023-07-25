import axios from 'axios';
const Genre_API_BASE_URL = "https://8080-fbecdbbdadebcceabbaeaeaadbdbabf.project.examly.io/api/genre";
const Genre_API_BASE_URL2 = "https://8080-fbecdbbdadebcceabbaeaeaadbdbabf.project.examly.io/admin/genre";
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