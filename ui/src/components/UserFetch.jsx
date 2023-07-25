import { useState, useEffect } from 'react';

function UserFetch() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [creds, setCreds] = useState(null);
  const [bigtoken, setBigtoken] = useState(null);
  const [isRole, setIsRole] = useState(null);
  useEffect(() => {
    let storedCreds = JSON.parse(localStorage.getItem('user'));
    if (storedCreds !== null) {
      setCreds(storedCreds);
      setIsLoggedIn(true);
      setIsRole(storedCreds.user_role[0].authority)
      setBigtoken(storedCreds.token)
    }
  }, []);

  return { isLoggedIn, creds, bigtoken, isRole };
}

export default UserFetch;