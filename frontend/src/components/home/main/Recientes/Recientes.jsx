// eslint-disable-next-line no-unused-vars
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Slider from "react-slick";
import "slick-carousel/slick/slick-theme.css";
import "slick-carousel/slick/slick.css";
import Heading from '../../../../components/heading/Heading.jsx';
import axios from 'axios';

export default function Popular() {
    const [recientes, setRecientes] = useState([]);

    useEffect(() => {
        obtenerNoticiasRecientes();
    }, []);

    const obtenerNoticiasRecientes = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/noticias/recientes');
            const data = response.data;
            setRecientes(data);
        } catch (error) {
            console.error('Error al obtener las noticias recientes: ', error);
            throw error;
        }
    };

    const settings = {
        className: "center",
        centerMode: false,
        infinite: false,
        centerPadding: "",
        slidesToShow: 2,
        speed: 500,
        rows: 3,
        slidesPerRow: 1,
        dots: false,
        autoplay: true,
        autoplaySpeed: 3000,
        pauseOnHover: true,
        responsive: [
            {
                breakpoint: 800,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1
                }
            }
        ]
    };

    return (
        <section className="popular">
            <Heading title="Recientes" />
            <Slider {...settings}>
            {recientes.map((noticia, index) => (
    <div className="items" key={index}>
        <div className="box shadow">
            <div className="images row">
                <div
                    className="img"
                    style={{ width: "100%", height: "auto", overflow: "hidden" }}
                >
                    <img
                        src={`http://localhost:8080/api/noticias/${noticia.id}/portada`}
                        alt={noticia.titulo}
                        style={{ maxWidth: "100%", height: "auto" }}
                    />
                </div>

                {noticia.seccion && (
                    <div className="categoria categoria1">
                        <Link to={`/seccion/${noticia.seccion.id}`}>
                            <span>{noticia.seccion.nombre}</span>
                        </Link>
                    </div>
                )}
            </div>
            <div className="text row">
                <Link to={`/noticia/${noticia.id}`}>
                    <h1 className="titulo">{noticia.titulo}</h1>
                </Link>
                <div className="fecha">
                    <i className="fas fa-calendar-days"></i>
                    <label htmlFor="">
                        {new Date (noticia.fechaPublicacion).toLocaleDateString()}
                    </label>{" "}
                    {/* Agrega el subtítulo aquí si es necesario */}
                </div>
            </div>
        </div>
    </div>
))}
            </Slider>
        </section>
    );
}
