import axios from 'axios';
const MOVIE_API_BASE_URL = `${process.env.REACT_APP_API_URL}api/movies`;
const MOVIE_API_BASE_URL2 = `${process.env.REACT_APP_API_URL}admin/movies`;
const TRENDING_API = `${process.env.REACT_APP_API_URL}api/trending`;
const TRENDING_API2 = `${process.env.REACT_APP_API_URL}admin/trending`;
class MovieService {

    getMovies() {
        return axios.get(MOVIE_API_BASE_URL);
    }

    createMovie(movie, token) {
        const config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return axios.post(MOVIE_API_BASE_URL2, movie, config);
    }

    getMovieById(movieId) {
        return axios.get(MOVIE_API_BASE_URL + '/' + movieId);
    }
    updateMovie(movie, movieId, token) {
        const config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return axios.put(MOVIE_API_BASE_URL2 + '/' + movieId, movie, config);
    }
    deleteMovie(movieId, token) {
        const config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return axios.delete(MOVIE_API_BASE_URL2 + '/' + movieId, config);
    }
    removeGenreFromMovie(genreId, movieId) {
        return axios.put(MOVIE_API_BASE_URL2 + '/' + genreId + '/' + movieId);
    }
    getMovieByGenre(category) {
        return axios.get(MOVIE_API_BASE_URL + '/search/' + 'genre?category=' + category)

    }
    getMovieByTitle(title) {
        return axios.get(MOVIE_API_BASE_URL + '/search/' + 'title?title=' + title)

    }

    getRecommendation() {
        return axios.get(MOVIE_API_BASE_URL + '/' + 'recommendation')
    }

    addToTrending(movieId, token) {
        const config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }
        console.log(config)
        return axios.get(TRENDING_API2 + '/' + movieId, config)
    }
    isTrending(movieId) {
        return axios.get(TRENDING_API + '/istrending/' + movieId)
    }
    removeFromTrending(movieId, token) {
        const config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return axios.delete(TRENDING_API2 + '/' + movieId, config)
    }
    getTrending() {
        return axios.get(TRENDING_API);
    }
}

export default new MovieService();