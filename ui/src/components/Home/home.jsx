import React from 'react';
import Trending from '../Trending/Trendingmovies';
import Genre from '../Genre/genre';
import Recommended from '../Recommended/Recommended';
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import AuthServices from '../Services/AuthServices';
import Navbar from '../Header/navbar';
import UserFetch from '../UserFetch';

const Home = () => {

  const { isLoggedIn, creds, bigtoken, isRole } = UserFetch();

  const logout = () => {
    AuthServices.logout();
    window.location.reload()
  }
  return (
    <>
      <Navbar />
      <br />
      <Recommended />
      <br />
      <Trending />
      <br />
      <Genre />
      <br />

    </>
  );
}

export default Home;