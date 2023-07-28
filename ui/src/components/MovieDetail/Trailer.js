import React from 'react';
import YouTube from 'react-youtube';

const Trailer = ({ videoId, height }) => {
  const width = '100%';
  const opts = {
    height: height,
    width: width,
    playerVars: {
      autoplay: 0,
      mute: 1,
      modestbranding: 1,
      loop: 1,
      playlist: videoId
    },
  };

  return <YouTube videoId={videoId} opts={opts} />;
};

export default Trailer;
