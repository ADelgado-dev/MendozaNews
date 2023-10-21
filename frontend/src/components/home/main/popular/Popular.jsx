/* eslint-disable react/jsx-key */
import { Link } from 'react-router-dom';
import Slider from "react-slick";
import "slick-carousel/slick/slick-theme.css";
import "slick-carousel/slick/slick.css";
import Heading from '../../../../components/heading/Heading.jsx';
import { popular } from '../../../../service/noticia/Principales.js';
import './popular.css';

export default function Popular() {

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
    <section className='popular'>
      <Heading title='Popular' />
      <Slider {...settings}>
        {popular.map((val) => {
          return (
            <div className="items">
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
          )
        })}
      </Slider>
    </section>
  );
}
