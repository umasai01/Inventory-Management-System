function Navbar({ onPageChange, onLogout, currentPage }) {
  return (
    <nav className="app-navbar">
      <div className="nav-content">

        <div className="nav-links">

          <button
            className={`nav-button ${
              currentPage === "purchase" ? "active" : ""
            }`}
            onClick={() => onPageChange("purchase")}
          >
            Purchase Entry
          </button>

          <button
            className={`nav-button ${
              currentPage === "report" ? "active" : ""
            }`}
            onClick={() => onPageChange("report")}
          >
            Purchase Report
          </button>

        </div>

        <button
          className="logout-button"
          onClick={onLogout}
        >
          Logout
        </button>

      </div>
    </nav>
  );
}

export default Navbar;