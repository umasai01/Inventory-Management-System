import { useState } from "react";

function Login({ onLogin }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleLogin = (event) => {
    event.preventDefault();

    const correctUsername = "admin";
    const correctPassword = "admin";

    if (username !== correctUsername || password !== correctPassword) {
      setErrorMessage("Invalid username or password.");
      return;
    }

    setErrorMessage("");
    onLogin();
  };

  return (
    <div className="login-page">
      <div className="login-card">
        <h2>Inventory Management System</h2>

        <p className="login-subtitle"> User Login </p>

        <form onSubmit={handleLogin}>
          <div className="login-group">
            <label>Username</label>

            <input type="text" value={username} onChange={(event) => setUsername(event.target.value)}
              placeholder="Enter username" required />
          </div>

          <div className="login-group">
            <label>Password</label>

            <input type="password" value={password} onChange={(event) => setPassword(event.target.value)}
              placeholder="Enter password" required />
          </div>

          {errorMessage && (<div className="login-error"> {errorMessage}
            </div>
          )}

          <button type="submit" className="login-button" >
            Login
          </button>
        </form>
      </div>
    </div>
  );
}

export default Login;