import { useState } from "react";
import { useForm } from "react-hook-form";
import { useDispatch } from "react-redux";
import { login } from "../../reducer/authActions.js";
import { setAuthHeader } from "../../helpers/axios_helper.jsx";
import axios from "axios";
import "../Login/login2.css";

const Login2 = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();
  const dispatch = useDispatch();
  const [error, setError] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const onSubmit = async (data) => {
    try {
      const response = await axios.post("http://localhost:8080/api/v1/entrar", {
        email: data.username,
        password: data.password,
      });

      const token = response.data.token;
      // Aquí puedes realizar acciones adicionales según la respuesta del backend
      setAuthHeader(token);
      dispatch(login(token));
      setSuccessMessage("Inicio de sesión exitoso.");
      setError("");
    } catch (error) {
      console.log (error.message)
      if (error.response && error.response.status === 404) {
        setError("El correo no está registrado.");
      } else {
        setError("Error al iniciar sesión. Verifica tus credenciales.");
      }
      setSuccessMessage("");
    }
  };

  return (
    <div className="form-container">
      <form onSubmit={handleSubmit(onSubmit)} className="form">
        {error && <span className="error-msg">{error}</span>}
        {successMessage && <span className="success-msg">{successMessage}</span>}

        <div>
          <label htmlFor="username">Usuario</label>
          <input
            type="text"
            id="username"
            {...register("username", {
              required: "Por favor, completa este campo.",
            })}
            className="input"
          />
          {errors.username && (
            <span className="error-msg">{errors.username.message}</span>
          )}
        </div>

        <div>
          <label htmlFor="password">Contraseña</label>
          <input
            type="password"
            id="password"
            {...register("password", {
              required: "Por favor, completa este campo.",
            })}
            className="input"
          />
          {errors.password && (
            <span className="error-msg">{errors.password.message}</span>
          )}
        </div>

        <div className="button-container">
          <button type="submit" className="send-button">
            Iniciar Sesión
          </button>
        </div>
      </form>
    </div>
  );
};

export default Login2;
