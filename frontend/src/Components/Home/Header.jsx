import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Navbar from "./Navbar";
import AuthModal from "../Modals/AuthModal";
import AuthService from "../../Services/AuthService";

const Header = () => {
  const navigate = useNavigate();
  const [isAuthModalOpened, setIsAuthModalOpened] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  
  useEffect(() => {
    const checkLoginStatus = () => {
      const isAuthenticated = AuthService.isAuthenticated();
      setIsLoggedIn(isAuthenticated);
    };
    checkLoginStatus();
    window.addEventListener("storage", checkLoginStatus);
    window.addEventListener("focus", checkLoginStatus);
    return () => {
      window.removeEventListener("storage", checkLoginStatus);
      window.removeEventListener("focus", checkLoginStatus);
    };
  }, []);
  
  const authButtonClicked = () => {
    if (isLoggedIn) {
      navigate("/community");
    } else {
      setIsAuthModalOpened(true);
    }
  };
  
  const handleAuthSuccess = () => {
    setIsAuthModalOpened(false);
    setIsLoggedIn(true);
    navigate("/");
  };
  
  return (
    <header className={`header ${isLoggedIn ? 'header--logged-in' : ''}`}>
      <Navbar />
      <div className="section__container">
        <div className="header__container">
          <div className="header__content">
            <h1>CODE SMARTER WITH CODEHUB</h1>
            <h2>BUILD. DEBUG. DEPLOY.</h2>
            <p>
              Accelerate your coding journey with expert tutorials, challenging projects, 
              and a supportive developer community. Your programming mastery starts here.
            </p>
            <div className="header__btn">
              <button className="btn btn__primary" onClick={authButtonClicked}>
                {isLoggedIn ? "My Dashboard" : "START CODING NOW"}
              </button>
            </div>
          </div>
        </div>
      </div>
      
      <div className="motivation-banner">
        <p>"Code is poetry in motion. Debug with patience." <strong>- CodeHub</strong></p>
      </div>
      
      <AuthModal
        onClose={() => setIsAuthModalOpened(false)}
        onSuccess={handleAuthSuccess}
        isOpen={isAuthModalOpened}
      />
    </header>
  );
};

export default Header;