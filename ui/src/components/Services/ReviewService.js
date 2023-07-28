import axios from 'axios';
const Review_API_BASE_URL1 = `${process.env.REACT_APP_API_URL}api/reviews`;
const Review_API_BASE_URL2 = `${process.env.REACT_APP_API_URL}api/reviews`;
const Review_API_BASE_URL3 = `${process.env.REACT_APP_API_URL}user/reviews`;
class ReviewService {

    getReview(movieId) {
        return axios.get(Review_API_BASE_URL1 + '/' + movieId);
    }
    deleteReview(reviewId, token) {
        const config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return axios.delete(Review_API_BASE_URL3 + '/' + reviewId, config);

    }
    postReview(movieId, review, token) {
        const config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }
        return axios.post(Review_API_BASE_URL3 + '/' + movieId, review, config);
    }
    putReview(reviewId, review, token) {
        const config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }
        return axios.put(Review_API_BASE_URL3 + '/' + reviewId, review, config);
    }

    getAllReviews() {
        return axios.get(Review_API_BASE_URL1);
    }
    getSpecificReviews(token) {
        const config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return axios.get(Review_API_BASE_URL3 + '/specific/', config);
    }
}

export default new ReviewService();