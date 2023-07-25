import React, { useState, useEffect } from 'react'
import { useLocation, useNavigate } from 'react-router-dom';
import './ViewAll.css';
import MovieServices from '../Services/MovieServices.js';
import ViewMovieModal from '../moviemodal';
import UserFetch from '../UserFetch';
import Navbar from '../Header/navbar.jsx';

const ViewAll = () => {
    const navigate = useNavigate();
    let [rmovie, setRmovie] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [selectedMovie, setSelectedMovie] = useState(null);
    const { isLoggedIn, creds, bigtoken, isRole } = UserFetch();

    const openMovie = (movieId) => {
        navigate(`/view-movie/${movieId}`);
    };
    const fetchMovies = async () => {
        MovieServices.getMovies()
            .then((res) => {
                if (Array.isArray(res.data)) {
                    setRmovie(res.data);
                }
            })
            .catch((error) => {
                console.error("Error fetching movies:", error);
            });
    };
    useEffect(() => {
        fetchMovies();
    }, []);

    const handleShowModal = (rmovie) => {
        setSelectedMovie(rmovie)
        setShowModal(true);
    };

    const handleCloseModal = () => {
        setShowModal(false);
    };
    const handleDelete = (id) => {
        MovieServices.deleteMovie(id, bigtoken).then(
            () => {
                rmovie = rmovie.filter((movie) => movie.movieId !== id);
                setRmovie(rmovie)

            }
        )
    };

    return (
        <>
            <Navbar />
            <div className="flex-fill" >

                <h2 id='headv' style={{ color: "gray", paddingLeft: "1.8rem" }}>Recommended & Trending</h2>
                <br />
                <div className="container mt-4" >
                    <div className="row" >
                        {rmovie.map((rmovie, index) => (
                            <div className="col-md-3 coller" key={rmovie.id} style={{ paddingBottom: '25px', width: '20 rem' }}>
                                <div className="card vcard" >
                                    {isRole == "ROLE_ADMIN" ? (
                                        <div className="card-delete">
                                            <i onClick={() => handleDelete(rmovie.movieId)} >
                                                <img src={"/delete.png"} height={25} width={25} />
                                            </i>
                                        </div>
                                    ) : (
                                        <></>
                                    )}

                                    <img
                                        src={rmovie.posterUrl}
                                        onClick={() => openMovie(rmovie.movieId)}
                                        className="card-img-top"
                                        alt={rmovie.title}
                                        style={{ objectFit: 'cover', height: '350px' }}
                                    />
                                    <div className="card-body" style={{ backgroundColor: 'black' }}>
                                        <h5 className="card-title" style={{
                                            color: 'wheat',
                                            whiteSpace: 'nowrap',
                                            textOverflow: 'ellipsis',
                                            overflow: 'hidden',
                                        }}>{rmovie.title}</h5>
                                        <a onClick={() => handleShowModal(rmovie)} className="btn btn-primary" style={{ backgroundColor: '#FFA500', borderColor: '#FFA500', color: 'black' }}>
                                            Watch Option
                                        </a>
                                    </div>
                                    {selectedMovie && <ViewMovieModal showModal={showModal} handleCloseModal={handleCloseModal} movie={{
                                        image: selectedMovie.posterUrl,
                                        title: selectedMovie.title,
                                        description: selectedMovie.movieDesc,
                                        rating: selectedMovie.rating,
                                    }} />}
                                </div>

                            </div>
                        ))}
                    </div>
                </div>
            </div>
        </>
    );
};
export default ViewAll;