import { useState } from "react";

import Header from "./components/Header";
import Navbar from "./components/Navbar";
import Login from "./components/Login";
import PurchaseEntry from "./components/PurchaseEntry";
import PurchaseReport from "./components/PurchaseReport";
import PurchaseSuccess from "./components/PurchaseSuccess";

import "./App.css";

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [page, setPage] = useState("purchase");
  const [successData, setSuccessData] = useState(null);

  const handleLogin = () => {
    setIsLoggedIn(true);
    setPage("purchase");
  };

  const handleLogout = () => {
    setIsLoggedIn(false);
    setSuccessData(null);
    setPage("purchase");
  };

  const showSuccess = (purchaseData) => {
    setSuccessData(purchaseData);
  };

  const showPurchaseEntry = () => {
    setSuccessData(null);
    setPage("purchase");
  };

  const changePage = (selectedPage) => {
    setSuccessData(null);
    setPage(selectedPage);
  };

  if (!isLoggedIn) {
    return <Login onLogin={handleLogin} />;
  }

  return (
    <div>
      <Header />

      <Navbar
        onPageChange={changePage}
        onLogout={handleLogout}
        currentPage={page}
      />

      {successData ? (
        <PurchaseSuccess
          purchaseData={successData}
          onBack={showPurchaseEntry}
        />
      ) : page === "purchase" ? (
        <PurchaseEntry onSuccess={showSuccess} />
      ) : (
        <PurchaseReport />
      )}
    </div>
  );
}

export default App;