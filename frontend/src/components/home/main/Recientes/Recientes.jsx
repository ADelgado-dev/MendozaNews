
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
    const obtenerNoticiasRecientes = async () => {
      try {
        const response = await axios.get('/api/noticias/recientes');
        if (response.status !== 200) {
          throw new Error('Error al obtener las noticias recientes');
        }
        const data = response.data;
        setRecientes(data);
      } catch (error) {
        console.error('Ha ocurrido un error:', error);
      }
    };
    obtenerNoticiasRecientes();
  }, []);

  const settings = {
    className: "center",
    centerMode: false,
    infinite: true,
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
    <section className='Recientes'>
      <Heading title='Recientes' />
      <Slider {...settings}>
        {recientes.map((val, index) => (
          <div className="items" key={index}>
            <div className="box shadow">
              <div className="images row">
                <div className="img">
                  <img src={val.imagen} alt="" />
                </div>

                <div className="categoria categoria1">
                  <Link to={`/seccion/${val.categoria}`}>
                    <span>{val.categoria}</span>
                  </Link>
                </div>
              </div>
              <div className="text row">
                <Link to={`/noticia/${val.titulo}`}>
                  <h1 className="titulo">{val.titulo}</h1>
                </Link>
                <div className="fecha">
                  <i className='fas fa-calendar-days'></i>
                  <label htmlFor=''>{val.fecha}</label>
                </div>
              </div>
            </div>
          </div>
        ))}
      </Slider>
    </section>
  );
}
